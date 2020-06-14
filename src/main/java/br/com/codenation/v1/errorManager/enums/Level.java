package br.com.codenation.v1.errorManager.enums;

import br.com.codenation.v1.errorManager.exception.LevelNotFoundException;

public enum Level {

    ERROR(0, "ERROR"),
    WARNING(1,"WARNING"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO");

    private int code;
    private final String level;

    Level(int code, String level){
        this.level = level;
    }

    public int getCode() {
        return code;
    }

    public String getLevel() {
        return level;
    }

    public static Level toEnum(Integer code){

        if (code == null){
            throw new NullPointerException("Level is required");
        }

        for (Level c : Level.values()){
            if (code.equals(c.getCode())){
                return c;
            }
        }

        throw new LevelNotFoundException();
    }
}
