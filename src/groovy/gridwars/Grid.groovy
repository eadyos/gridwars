package gridwars

/**
 * Created by steve on 1/9/15.
 */
class Grid {

    static final int gridWidth = 50
    static final int gridHeight = 50

    GridUnit[][] gridUnits = new GridUnit[gridWidth][gridHeight]
    List<Algo> algos = []

    public Grid(){
        (0..gridHeight - 1).each{heightIndex ->
            (0..gridWidth -1).each{widthIndex ->
                gridUnits[heightIndex][widthIndex] = new GridUnit()
            }
        }
    }

    public executeAlgos(){
        Integer[] state = new Integer[8]
        GridUnit[] neighbors = new GridUnit[8]

        (0..gridHeight - 1).each{heightIndex ->
            (0..gridWidth -1).each{widthIndex ->

                GridUnit gridUnit = gridUnits[heightIndex][widthIndex]
                if(gridUnit.algo){

                    //upperLeft
                    neighbors[0] = getNeighbor(heightIndex - 1, widthIndex - 1)
                    state[0] = getNeighborState(neighbors[0], gridUnit)
                    //upper
                    neighbors[1] = getNeighbor(heightIndex - 1, widthIndex)
                    state[1] = getNeighborState(neighbors[1], gridUnit)
                    //upperRight
                    neighbors[2] = getNeighbor(heightIndex - 1, widthIndex + 1)
                    state[2] = getNeighborState(neighbors[2], gridUnit)
                    //left
                    neighbors[3] = getNeighbor(heightIndex, widthIndex - 1)
                    state[3] = getNeighborState(neighbors[3], gridUnit)
                    //right
                    neighbors[4] = getNeighbor(heightIndex, widthIndex + 1)
                    state[4] = getNeighborState(neighbors[4], gridUnit)
                    //lowerLeft
                    neighbors[5] = getNeighbor(heightIndex + 1, widthIndex - 1)
                    state[5] = getNeighborState(neighbors[5], gridUnit)
                    //lower
                    neighbors[6] = getNeighbor(heightIndex + 1, widthIndex)
                    state[6] = getNeighborState(neighbors[6], gridUnit)
                    //lowerRight
                    neighbors[7] = getNeighbor(heightIndex + 1, widthIndex + 1)
                    state[7] = getNeighborState(neighbors[7], gridUnit)

                    Integer[] result = gridUnit.algo.algo(state)
                    if(result.sum() <= 3){
                        (0..7).each {idx ->
                            if(result[idx] > 0){
                                neighbors[idx].addBullets(gridUnit.algo, result[idx])
                            }
                        }
                    }
                }
            }
        }

        (0..gridHeight - 1).each{widthIndex ->
            (0..gridWidth -1).each{heightIndex ->

                GridUnit gridUnit = gridUnits[heightIndex][widthIndex]
                gridUnit.processBullets()
            }
        }
    }

    private GridUnit getNeighbor(int yPos, int xPos){
        if(yPos < 0){
            yPos += gridHeight
        }
        if(xPos < 0){
            xPos += gridWidth
        }
        if(yPos >= gridHeight){
            yPos -= gridHeight
        }
        if(xPos >= gridWidth){
            xPos -= gridWidth
        }
        GridUnit neighborUnit = gridUnits[yPos][xPos]
        return neighborUnit
    }

    private int getNeighborState(GridUnit neighborUnit, GridUnit currentGridUnit){
        int state
        if(!neighborUnit.algo){
            state = 0
        }else if(neighborUnit.algo.id == currentGridUnit.algo.id){
            state = 1
        }else{
            state = 2
        }
        return state
    }

    public seedPlayers(){

        algos.each {algo ->

            algo.algo = Eval.me(algo.algoText)

            boolean isPlaced = false
            while(!isPlaced){
                isPlaced = true
                def coordinates = getRandomCoordinates()
                def yPos = coordinates[0]
                def xPos = coordinates[1]
                //check to make sure we have some reasonable starting space
                //5x5 box perhaps, which is 2 spaces in every direction
                (0..2).each{yBound ->
                    (0..2).each{xBound ->
                        def testX = xPos - xBound
                        if(testX < 0){
                            testX += gridWidth
                        }
                        def testY = yPos - yBound
                        if(testY < 0){
                            testY += gridHeight
                        }
                        if(gridUnits[testY][testX].algo){
                            isPlaced = false
                        }
                    }
                }
                if(isPlaced){
                    gridUnits[yPos][xPos].algo = algo
                }
            }
        }
    }

    private List<Integer> getRandomCoordinates(){

        int xPos = (int)(Math.random() * gridWidth)
        int yPos = (int)(Math.random() * gridHeight)
        return [yPos, xPos]
    }

}
