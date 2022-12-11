package org.iesch.myapplication.ui.location;

public class Lugar {

    private String nombre;
    private String localidad;
    private String horario;
    private String type;
    private String ambiente;
    private int imagenId;


    public Lugar(String nombre, String localidad, String horario, String type, String ambiente, int imagenId) {
        this.nombre = nombre;
        this.localidad = localidad;
        this.horario = horario;
        this.type = type;
        this.ambiente = ambiente;
        this.imagenId = imagenId;
    }


    public String getNombre() {
        return nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getHorario() {
        return horario;
    }

    public String getType() {
        return type;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public int getImagenId() {
        return imagenId;
    }
}
