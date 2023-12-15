package com.github.thedreps.autowhoforge;

public class ServerInfo {

    public String server = "default";
    public String gametype = "default";
    public String mode = "default";
    public String map = "default";
    public String lobbyname = "default";
    public String json = "default";

    public void reset() {
        this.server = "default";
        this.gametype = "default";
        this.mode = "default";
        this.map = "default";
        this.lobbyname = "default";
        this.json = "default";

    }

    public String debug() {
        return "server: " + server + ", gameType: " + gametype + ", mode: " + mode + ", map: " + map + ", lobbyName: " + lobbyname + ", JSON: " + json;
    }
}

