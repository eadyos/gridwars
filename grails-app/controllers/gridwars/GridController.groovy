package gridwars

import grails.converters.JSON
import org.springframework.web.multipart.commons.CommonsMultipartFile

class GridController {

    def springSecurityService

    def index(){
        User user = springSecurityService.currentUser
        def users = []
        if(springSecurityService.currentUser.isAdmin()){
            users.addAll(User.list())
        }else{
            users << user
            if(user.username != 'guest'){
                users << User.findByUsername('guest')
            }
        }
        render(view: "index", model: [sessionAlgos: session.algos ?: [], users: users, currentUser: springSecurityService.currentUser])
    }

    def createGrid(){

        def algoIds = []
        params.each {k,v ->
            if(v == 'on'){
                def parts = k.split("_")
                algoIds << parts[1]
            }
        }
        if(algoIds.size() > 8 || algoIds.size() == 0){
            redirect(action: 'index')
            return
        }

        def algos = Algo.findAllByIdInList(algoIds.collect {it as Long})
        def verifiedAlgos = []
        algos.each {algo ->
            if(algo.user.username in [springSecurityService.currentUser.username, 'guest'] ||
                    springSecurityService.currentUser.isAdmin()
            ){
                verifiedAlgos << algo
            }
        }

        session.algos = verifiedAlgos
        session.speed = params.speed ?: 1000

        redirect(action: 'showGrid')
    }

    def showGrid(){

        session.grid = new Grid()
        session.grid.algos = session.algos
        session.grid.seedPlayers()

        def gridUnits = session.grid.gridUnits
        def gridData = gridUnits.collect {row ->
            row.collect {col ->
                col.algo?.id ?: 0
            }
        }

        render(view: "grid", model: [gridData: (gridData as JSON), gridSpeed: session.speed])
    }

    def getPlayers(){

        def players = session.grid.algos

        def playerData = players.collect {[name: it.name, id: it.id]}

        render playerData as JSON
    }

    def getGrid(){

        session.grid.executeAlgos()

        def gridUnits = session.grid.gridUnits
        def gridData = gridUnits.collect {row ->
            row.collect {col ->
                col.algo?.id ?: 0
            }
        }

        render gridData as JSON
    }

    def addAlgo(){

        CommonsMultipartFile file = params.algoFile
        def text = params.algoText
        def name = params.algoName ?: "No Name"

        if(!text){
            text = new String(file.bytes)
        }

        if(isValidEntry(text)){
            Algo algo = new Algo()
            algo.algoText = text
            algo.name = name
            User user = springSecurityService.currentUser
            user.addToAlgos(algo)
            user.save(flush:true)
        }else{
            flash.message = "Invalid closure"
        }


        redirect(action: 'index')
    }

    def deleteAlgo(Algo algo){
        if(algo.user.id == springSecurityService.currentUser.id){
            algo.delete(flush:true)
        }
        redirect(action: 'index')
    }

    def showCreateUser(){

        render(view: 'createUser')
    }

    def createUser(){

        def username = params.username
        def password = params.password
        def name = params.name

        if(username && password && name){
            User user = new User()
            user.username = username
            user.password = password
            user.name = name
            user.save(flush:true)
            UserRole.create(user, Role.findByAuthority('ROLE_USER'), true)
            flash.message = "User Created."
            redirect(action: 'index')
        }else{
            flash.message = "Please enter all fields."
            redirect(action: 'showCreateUser')
        }

    }

    private boolean isValidEntry(String text){

        boolean isValid = true
        try{
            Closure algo = Eval.me(text)

            def input = new Integer[8]
            (0..7).each{
                input[it] = 0
            }
            def output = algo(input)

            if(output == null || !isCollectionOrArray(output) || output.size() != 8){
                isValid = false
            }
        }catch (Exception e){
            isValid = false
        }

        return isValid
    }

    private boolean isCollectionOrArray(object) {
        return object.class.isArray() || [Collection, Object[]].any { it.isAssignableFrom(object.getClass()) }
    }

}
