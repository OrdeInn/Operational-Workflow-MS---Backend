package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

public enum ESupplyRequestStatus {

    WAITING_WAREHOUSE {
        @Override
        public String toString() {

            return "Waiting Warehouse";
        }
    },
    OUT_OF_STOCK {
        @Override
        public String toString() {

            return "Out of Stock";
        }
    },
    DONE {
        @Override
        public String toString() {

            return "Done";
        }
    };

    public static ESupplyRequestStatus fromString(String input) {

        switch (input){
            case("Waiting Warehouse"):
                return WAITING_WAREHOUSE;

            case("Out of Stock"):
                return OUT_OF_STOCK;

            case("Done"):
                return DONE;
        }
        return null;
    }
}
