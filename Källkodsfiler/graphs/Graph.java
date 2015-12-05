package graphs;

import java.util.*;

interface Graph<N> {

    void connect(N from, N to, String namn, int vikt);

    List<Edge<N>> getEdgesFrom(N from);

    Set<N> getNodes();

    void add(N Nod);

    Edge getEdgeBetween(N s1, N s2);

    void setConnectionWeight(N from, N to, int nyVikt);
    
    
    
    
}
