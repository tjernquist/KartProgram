package graphs;

import java.util.*;

public class ListGraph<N> implements Graph<N> {

    private Map<N, List<Edge<N>>> nodes = new HashMap<>();

    public Edge<N> getEdgeBetween(N s1, N s2) {

        Edge<N> förbindelse = null;

        if (!nodes.containsKey(s1) || !nodes.containsKey(s2)) {
            throw new NoSuchElementException("Staden " + s2 + " och/eller staden " + s1 + " finns inte");
        }
        for (Edge<N> e : nodes.get(s1)) {
            if (e.getDestination().equals(s2) == true) {
                förbindelse = e;
            }
        }
        return förbindelse;
    }

    public void add(N ny) {
        if (nodes.containsKey(ny)) {
            throw new IllegalArgumentException("Stad finns redan");
        }
        nodes.put(ny, new ArrayList<Edge<N>>());
    }

    public void connect(N from, N to, String namn, int vikt) {

        if (from == null || to == null) {
            throw new IllegalArgumentException("En av noderna är null");
        }

        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("En eller både noder fattas");
        }
        if (vikt < 0) {
            throw new IllegalArgumentException("Vikten får inte vara negativ");
        }
        if (getEdgeBetween(from, to) != null) {
            throw new IllegalStateException("Det finns redan en connection mellan de två platserna");
        }

        List<Edge<N>> toList = nodes.get(to);
        List<Edge<N>> fromList = nodes.get(from);

        if (fromList == null || toList == null) { //kanske ta bort
            throw new NoSuchElementException("Ingen konnektion");
        }

        Edge<N> e1 = new Edge<>(to, namn, vikt);
        Edge<N> e2 = new Edge<>(from, namn, vikt);

        fromList.add(e1);
        toList.add(e2);
    }

    public void setConnectionWeight(N from, N to, int nyVikt) {

        if (nyVikt < 0) {
            throw new IllegalArgumentException("Vikt får inte vara negativ!");
        }

        Edge<N> e1 = getEdgeBetween(to, from);
        Edge<N> e2 = getEdgeBetween(from, to);

        if (e1 == null || e2 == null) {
            throw new NoSuchElementException("Det finns ingen förbindelse mellan noderna");
        }
        e1.setWeight(nyVikt);
        e2.setWeight(nyVikt);

    }

    @Override
    public List<Edge<N>> getEdgesFrom(N from) {
        if (nodes.containsKey(from)) {
            return nodes.get(from);
        } else {
            throw new NoSuchElementException("Elementet " + from + " finns ej!");
        }
    }

    public Set<N> getNodes() {

        return nodes.keySet();

    }

    public String toString() {
        String str = "";
        for (Map.Entry<N, List<Edge<N>>> me : nodes.entrySet()) {
            str += me.getKey() + ": ";
            for (Edge e : me.getValue()) {
                str += e.toString() + " ";
            }
            str += "\n";
        }
        return str;
    }
}
