package object_orinted_design.locker;

public interface ILockerSelector {

    public Locker request(Criteria criteria);

    public boolean assign(String userId, String lockerId);

    public void release(String userId, String lockerId);


    class Criteria {

        private String userId;

        private long allocationStartTime;

        private long allocationEndTime;

        private int volume;

        private int weight;

        public String getUserId() {
            return userId;
        }

        public long getAllocationStartTime() {
            return allocationStartTime;
        }

        public long getAllocationEndTime() {
            return allocationEndTime;
        }

        public int getVolume() {
            return volume;
        }

        public int getWeight() {
            return weight;
        }

        public Criteria() {
        }

        public Criteria(String userId, long allocationStartTime, long allocationEndTime, int volume, int weight) {
            this.userId = userId;
            this.allocationStartTime = allocationStartTime;
            this.allocationEndTime = allocationEndTime;
            this.volume = volume;
            this.weight = weight;
        }
    }
}
