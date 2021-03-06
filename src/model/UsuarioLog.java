package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by mateo on 20/05/15.
 */
@DatabaseTable(tableName = "UsuarioLog")
public class UsuarioLog {

        @DatabaseField(generatedId = true)
        private long id;
        @DatabaseField
        private String Op;
        @DatabaseField
        private String fecha;
        @DatabaseField
        private long idUsuario;

        public UsuarioLog(String Op, String fecha, long idUsuario) {
            this.Op = Op;
            this.fecha = fecha;
            this.idUsuario = idUsuario;
        }

        public UsuarioLog() {
        }



        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOperacion() {
            return Op;
        }

        public void setOperacion(String operacion) {
            this.Op = operacion;
        }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(long idUsuario) {
            this.idUsuario = idUsuario;
        }
    }


