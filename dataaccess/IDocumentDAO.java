package dataaccess;

import domain.Document;
import java.sql.SQLException;

public interface IDocumentDAO {
    public void addDocument(Document document);
    public void deleteDocument(String name);
    public void visualizeDocument(String name);
}
