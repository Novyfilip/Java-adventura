package cz.vse.novf02.main;
//pozorovaný

/**
 * Předmět pozorování pro návrhový vzor Observer
 */
public interface PredmetPozorovani {
    /** Regustruje změny u pozorovatele
     * @param zmenahry změna hry definovaná v Enum ZmenaHry
     * @param pozorovatel pozorovatel
     */
    void registruj(ZmenaHry zmenahry, Pozorovatel pozorovatel);
}
