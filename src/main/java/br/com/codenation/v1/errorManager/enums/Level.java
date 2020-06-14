package br.com.codenation.v1.errorManager.enums;

import br.com.codenation.v1.errorManager.exception.LevelNotFoundException;

public enum TypeLevel {

    ERROR(0, "ERROR"),
    WARNING(1,"WARNING"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO");

    private int code;
    private final String level;

    TypeLevel(int code, String level){
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static TypeLevel toEnum(String level){

        if (level == null){
            throw new NullPointerException("Level is required");
        }

        for (TypeLevel l : TypeLevel.values()){
            if (level.equalsIgnoreCase(l.level)){
                return l;
            }
        }

        throw new LevelNotFoundException();
    }
}
