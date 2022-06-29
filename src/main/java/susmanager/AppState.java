package susmanager;

import fundur.systems.lib.Entry;
import fundur.systems.lib.sec.EncrState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private boolean local;
    private List<Entry> pwds;
    private String user;
    private String password;

    private EncrState encrstate;
    private File encrypted;

    private String url;

    public AppState() {
        pwds = new ArrayList<>();
    }

    public boolean isLocal() {
        return local;
    }

    public List<Entry> pwds() {
        return pwds;
    }

    public String user() {
        return user;
    }

    public String password() {
        return password;
    }

    public EncrState encrstate() {
        return encrstate;
    }

    public File encrypted() {
        return encrypted;
    }

    public String url() {
        return url;
    }

    public AppState setLocal(boolean local) {
        this.local = local;
        return this;
    }

    public AppState setPwds(List<Entry> pwds) {
        this.pwds = pwds;
        return this;
    }

    public AppState setUser(String user) {
        this.user = user;
        return this;
    }

    public AppState setPassword(String password) {
        this.password = password;
        return this;
    }

    public AppState setEncrstate(EncrState encrstate) {
        this.encrstate = encrstate;
        return this;
    }

    public AppState setEncrypted(File encrypted) {
        this.encrypted = encrypted;
        return this;
    }

    public AppState setUrl(String url) {
        this.url = url;
        return this;
    }
}
