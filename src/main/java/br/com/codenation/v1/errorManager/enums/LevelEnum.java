package br.com.codenation.v1.errorManager.enums;

import br.com.codenation.v1.errorManager.exception.LevelNotFoundException;

public enum LevelEnum {

    ERROR("ERROR"),
    WARNING("WARNING"),
    DEBUG("DEBUG");

    private String level;

    LevelEnum(String level){
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static LevelEnum toEnum(String level){

        if (level == null){
            throw new NullPointerException("Level is required");
        }

        for (LevelEnum l : LevelEnum.values()){
            if (level.equalsIgnoreCase(l.level)){
                return l;
            }
        }

        throw new LevelNotFoundException();
    }
}
