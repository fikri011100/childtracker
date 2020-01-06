package com.titi.remotbayi.eventbus;

public class BabyNameBus {

    public static class EventBus {
        public String name;

        public EventBus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
