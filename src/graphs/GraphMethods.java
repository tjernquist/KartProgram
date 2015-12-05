package graphs;

import java.util.*;

public class GraphMethods<N> {

    private static <N> void dfs2(N where, N whereFrom, Set<N> besökta, Map<N, N> via, Graph<N> g) {

        besökta.add(where);
        via.put(where, whereFrom);
        
        for (Edge<N> e : g.getEdgesFrom(where)) { 
            if (!besökta.contains(e.getDestination())) {
                dfs2(e.getDestination(), where, besökta, via, g);
            }
        }
    }

    public static <N> boolean pathExists(N from, N to, Graph<N> g) {
        Map<N, N> via = new HashMap<>();
        Set<N> besökta = new HashSet<>(); 
        dfs2(from, null, besökta, via, g);
        return besökta.contains(to);
    }


    public static <N> ArrayList<Edge<N>> snabbasteVäg(N från, N till, Graph<N> g) {
        
        Map<N, Integer> tid = new HashMap<>();
        Map<N, Boolean> bästaVäg = new HashMap<>();
        Map<N,N> via = new HashMap<>();
        
        if (pathExists(från, till, g) != true) {
            throw new IllegalArgumentException("Det finns ingen koppling mellan städerna");
        } else {
            for (N nod : g.getNodes()){
                tid.put(nod, Integer.MAX_VALUE);
                bästaVäg.put(nod, Boolean.FALSE);
            }
            tid.put(från, 0);
            bästaVäg.put(från, Boolean.TRUE);
            
            N nuvarandeNod = från;
            
            while (!bästaVäg.get(till) == true) {
                for (Edge<N> e : g.getEdgesFrom(nuvarandeNod)){
                   if (tid.get(nuvarandeNod) + e.getVikt() < tid.get(e.getDestination())) {
                       tid.put(e.getDestination(), tid.get(nuvarandeNod) + e.vikt);
                       via.put(e.getDestination(), nuvarandeNod);
                   }
                }
                
                int minimalDist = Integer.MAX_VALUE;
                
                for (N nod : g.getNodes()) {
                    if (!bästaVäg.get(nod) && tid.get(nod) < minimalDist) {
                        minimalDist = tid.get(nod);
                        nuvarandeNod = nod;
                    }
                }
                bästaVäg.put(nuvarandeNod, Boolean.TRUE);
            }
            
            ArrayList<Edge<N>> fullständigVäg = new ArrayList<Edge<N>>();
            N destination = till;
            N föregående = via.get(destination);
            
            while (föregående != null) {
                fullständigVäg.add(g.getEdgeBetween(föregående, destination));
                destination = föregående;
                föregående = via.get(föregående);
            }
            
            Collections.reverse(fullständigVäg);
            return fullständigVäg;
        }
    }
}
