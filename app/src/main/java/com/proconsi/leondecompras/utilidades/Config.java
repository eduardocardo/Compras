package com.proconsi.leondecompras.utilidades;

/**
 * Created by rodrigo.gomez on 15/07/2015.
 */
public class Config {

    public static String accessToken;
    public static String refreshToken;
    public static String tokenExpireAt;

    public static final String CLIENT_ID = "password";
    public static final String CLIENT_SECRET = "android";
    public static final String USERNAME = "proconsi";
    public static final String PASSWORD = "0<zys{3PB8J38*";

    public static final boolean PCI = true;
    public static final String URL_BASE_API = PCI ? "http://www.leondecompras.pci/api/v1/" : "";
    public static final String URL_BASE_OAUTH = PCI ? "" : "";

    public static final long TIME_OUT_COMUNICACIONES = 10000;

    public static final String URL_API_CATEGORIAS = URL_BASE_API + "categorias";
    public static final String URL_API_ARTICULOS = URL_BASE_API + "articulos";

    public static final String URL_API_GENERAR_TOKEN = URL_BASE_OAUTH + "access_token";
    public static final String URL_API_REFRESCAR_TOKEN = URL_BASE_OAUTH + "renew_access_token";
}
