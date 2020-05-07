package domain;

import java.io.File;

public class Document {
    private String name;
    private String route;
    private byte[] document;

    public Document(){

    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public String getRoute() { return route; }

    public void SetRoute(String route) { this.route = route; this.convertDocumentBinary(); }

    public byte[] getDocument() { return document; }

    public void setDocument(byte[] document) { this.document = document; }

    public void convertDocumentBinary(){
        File file = new File(route);
        document = new byte[(int) file.length()];
        this.setDocument(document);
    }
}
