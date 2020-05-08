package dataaccess;

import domain.Document;

/**
 * Creation of the interface IDocumentDAO
 * @author Ivana Correa
 * @version 08/05/2020
 */

public interface IDocumentDAO {
    public void addDocument(Document document);
    public void deleteDocument(String name);
    public void visualizeDocument(String name);
}
