public class Trail implements Comparable<Trail>{
    public Location source;
    public Location destination;
    public int danger;

    public Trail(){};
    public Trail(Location source, Location destination, int danger) {
        this.source = source;
        this.destination = destination;
        this.danger = danger;
    }

    public Location either()
    { return source;
    }

    public Location other(Location location)
    {
        if (location == source) return destination;
        else return source;
    }

    public int compareTo(Trail that)
    {
        if (this.danger < that.danger) return -1;
        else if (this.danger > that.danger) return 1;
        else return 0;
    }

}
