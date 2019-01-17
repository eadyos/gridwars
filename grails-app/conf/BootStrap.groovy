import gridwars.Algo
import gridwars.Role
import gridwars.User
import gridwars.UserRole

class BootStrap {

    def init = { servletContext ->


        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def guestUser = new User(username: 'guest', enabled: true, password: 'guest', name: "Guest")
        def adminUser = new User(username: 'admin', enabled: true, password: 'the_Admin', name: "Admin")
        guestUser.save(flush: true)
        adminUser.save(flush: true)

        UserRole.create(guestUser, userRole, true)
        UserRole.create(adminUser, userRole, true)
        UserRole.create(adminUser, adminRole, true)

        def simpleAlgoText = """

            {state ->

                int randomDirection = (int)(Math.random() * 8)
                Integer[] shots = new Integer[8]
                (0..7).each{
                    shots[it] = 0
                }
                shots[randomDirection] = 3;

                return shots
            }
        """

        def stupidAlgoText = """

            {state ->

                int randomNum = (int)(Math.random() * 8)

                int randomDirection = (int)(Math.random() * 8)
                Integer[] shots = new Integer[8]
                (0..7).each{
                    shots[it] = 0
                }
                if(randomNum != 0){
                    shots[randomDirection] = 3;
                }

                return shots
            }
        """

        def cheaterAlgo = """

            {state ->

                int randomDirection = (int)(Math.random() * 8)
                int[] shots = new int[8]
                (0..7).each{
                    shots[it] = 0
                }
                shots[randomDirection] = 4;

                return shots
            }
        """

        Algo entry1 = new Algo(name: "L33t", algoText: simpleAlgoText, user: guestUser)
        entry1.save(flush:true)
        Algo entry2 = new Algo(name: "Chump", algoText: stupidAlgoText, user: guestUser)
        entry2.save(flush:true)
        Algo entry3 = new Algo(name: "Dweeb", algoText: stupidAlgoText, user: guestUser)
        entry3.save(flush:true)
    }
    def destroy = {
    }
}
