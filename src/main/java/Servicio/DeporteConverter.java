package Servicio;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import Entidades.Pista;

@Converter(autoApply = true)
public class DeporteConverter implements AttributeConverter<Pista.Deporte, String> {
    @Override
    public String convertToDatabaseColumn(Pista.Deporte deporte) {
        return deporte != null ? deporte.getDeporte() : null;
    }

    @Override
    public Pista.Deporte convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (Pista.Deporte d : Pista.Deporte.values()) {
            if (d.getDeporte().equals(dbData)) return d;
        }
        throw new IllegalArgumentException("Deporte no válido: " + dbData);
    }
}
