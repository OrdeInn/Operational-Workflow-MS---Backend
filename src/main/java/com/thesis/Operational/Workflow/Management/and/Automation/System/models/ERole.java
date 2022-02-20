package com.thesis.Operational.Workflow.Management.and.Automation.System.models;

public enum ERole {

    ROLE_SYSTEM_ADMIN {

        @Override
        public String toString() {

            return "System Admin";
        }
    },
    ROLE_OFFICE_WORKER {

        @Override
        public String toString() {

            return "Office Worker";
        }
    },
    ROLE_WAREHOUSE_WORKER {

        @Override
        public String toString() {

            return "Warehouse Worker";
        }
    },
    ROLE_FACTORY_WORKER {

        @Override
        public String toString() {

            return "Factory Worker";
        }
    },
    ROLE_MANAGER {

        @Override
        public String toString() {

            return "Manager";
        }
    };

    public static ERole fromString(String input) {

        switch (input){
            case("System Admin"):
                return ROLE_SYSTEM_ADMIN;

            case("Office Worker"):
                return ROLE_OFFICE_WORKER;

            case("Warehouse Worker"):
                return ROLE_WAREHOUSE_WORKER;

            case("Factory Worker"):
                return ROLE_FACTORY_WORKER;

            case("Manager"):
                return ROLE_MANAGER;
        }

        return null;
    }
}
