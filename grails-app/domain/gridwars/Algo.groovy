package gridwars

class Algo {

    Boolean active = false

    String name
    String algoText
    transient def algo

    static constraints = {
    }

    static transients = ['algo']

    static mapping = {
        algoText sqlType: 'text'
    }

    static belongsTo = [user: User]

}
