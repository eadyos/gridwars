package gridwars
/**
 * Created by steve on 1/9/15.
 */
class GridUnit {

    Algo algo
    Map<Algo, Integer> bulletCountMap = new HashMap<Algo, Integer>()

    public void addBullets(Algo player, int countParam){
        int count = bulletCountMap.get(player) ?: 0
        count += countParam
        bulletCountMap.put(player, count)
    }

    public void processBullets(){

        Map<Integer, List<Algo>> inverseMap = new TreeMap<Integer, List<Algo>>()

        bulletCountMap.each {algoParam, countParam ->
            List<Algo> algos = inverseMap.get(countParam)
            if(!algos){
                algos = []
                inverseMap.put(countParam, algos)
            }
            algos << algoParam
        }

        if(inverseMap.size() > 0){
            def entry = inverseMap.firstEntry()
            def size = entry.value?.size() ?: 0
            if(size == 1 && entry.key >= 3){
                algo = entry.value.first()
            }
        }

        bulletCountMap.clear()
    }

}
