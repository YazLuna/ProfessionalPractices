package domain;

import java.io.File;

public class Document {
    private String name;
    private byte[] document;

    public Document(){

    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name;}

    public byte[] getDocument() { return document; }

    public void setDocument(byte[] document) { this.document = document; }

}
