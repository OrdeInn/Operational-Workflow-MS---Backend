package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

public enum EStatus {

    WAITING_FOR_GRAPHICS_DESIGNER{
        @Override
        public String toString() {

            return "Waiting for Graphics Designer";
        }
    },

    WAITING_FOR_PRODUCTION{
        @Override
        public String toString() {

            return "Waiting for Production";
        }
    },

    SHIPPING_TO_WAREHOUSE{
        @Override
        public String toString() {

            return "Shipping to Warehouse";
        }
    },

    WAITING_FOR_DELIVER{
        @Override
        public String toString() {

            return "Waiting for Deliver";
        }
    },

    DELIVERED{
        @Override
        public String toString() {

            return "Delivered";
        }
    };

    public static EStatus fromString(String input) {

        switch (input){
            case("Waiting for Graphics Designer"):
                return WAITING_FOR_GRAPHICS_DESIGNER;

            case("Waiting for Production"):
                return WAITING_FOR_PRODUCTION;

            case("Shipping to Warehouse"):
                return SHIPPING_TO_WAREHOUSE;

            case("Waiting for Deliver"):
                return WAITING_FOR_DELIVER;

            case("Delivered"):
                return DELIVERED;
        }

        return null;
    }
}
