package graphs;

public class Edge<E> {   

    private E destination;
    String namn;
    int vikt;

    Edge(E destination, String namn, int vikt) {

        if (vikt < 0) {
            throw new IllegalArgumentException("Negativ vikt");
        }

        this.namn = namn; 
        this.vikt = vikt;
        this.destination = destination;
    }

    public String toString() {
        return "till " + destination + " med " + namn + " tar " + vikt + " timmar ";
    }

    public E getDestination() {
        return destination;
    }

    public String getNamn() {
        return namn;
    }

    public int getVikt() {
        return vikt;
    }

    public void setWeight(int nyVikt) {
        if (nyVikt > 0) {
            this.vikt = nyVikt;
        } else {
            throw new IllegalArgumentException("Vikten får inte vara negativ");
        }
    }
}
