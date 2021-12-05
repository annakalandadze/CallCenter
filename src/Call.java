import java.util.Comparator;
import java.util.Random;

public class Call implements Comparator<Call> {
    private final int id;
    private int duration;
    private int rank;

    /**
     * Constructor for call: rank can be 0, 1 or 2, while id is from 0 to 200 (exclusive)
     * @param duration of a call
     */
    public Call(int duration)  {
        this.duration = duration;
        this.rank = new Random().nextInt(3);
        this.id = new Random().nextInt(200);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * The system was implemented in such way that all calls are processed at the same time,
     * assuming that for each employee there is separate line.
     * However, if there is only one line, when improving the application, calls with highest priority will be answered first.
     * @param o1 first call
     * @param o2 second call
     * @return call with priority based on rank
     */
    @Override
    public int compare(Call o1, Call o2) {
        return -Integer.compare(o1.rank, o2.rank);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Call { " + id +
                " duration=" + duration +
                ", rank=" + rank +
                '}';
    }
}
