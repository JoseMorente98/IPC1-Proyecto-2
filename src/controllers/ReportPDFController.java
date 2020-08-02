/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Service;
import beans.SpareParts;
import beans.WorkOrder;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author Jose Morente
 */
public class ReportPDFController {
    private static ReportPDFController instance;
    public static ReportPDFController getInstance() {
        if(instance == null) {
            instance = new ReportPDFController();
        }
        return instance;
    }
    
    /**
     *VARIABLES 
     */
    private ServiceAssignament tope;
    private SparePartTopAssignament end;
    
    public void getTopService() {
        //IMPRIMIR SERVICIOS SIN ORDENAR Y ASIGNARLO
        tope = null;
        Service aux = null;
        aux = ServicesController.getInstance().first;
        WorkOrder work = null;
        
        while(aux != null) {
            System.out.println(aux.getName());
            int quantity = 0;
            work = WorkOrderController.getInstance().first;
            while(work != null) {
                if(aux.getName().equalsIgnoreCase(work.getServiceName())) {
                    quantity++;
                }
                work = work.getNext();
            }
            System.out.println(quantity);
            addAssignmentService(new ServiceAssignament(aux, quantity));
            aux = aux.getNext();
        }
        
        //METODO DE ORDENAMIENTO DE BURBUJA
        ServiceAssignament aux1, aux2;
        aux2 = tope;
        while (aux2 != null) {
            aux1 = aux2.getNext();
            while (aux1 != null) {
                if (aux1.getQuantity() > aux2.getQuantity()) {
                    Service auxiliar = aux2.getService();
                    int auxiliar1 = aux2.getQuantity();
                    
                    aux2.setService(aux1.getService());
                    aux2.setQuantity(aux1.getQuantity());
                    
                    aux1.setService(auxiliar);
                    aux1.setQuantity(auxiliar1);
                }
                aux1 = aux1.getNext();
            }
            aux2 = aux2.getNext();
        }
        
        PdfWriter writer = null;
        Document document = new Document();
        try {
            writer = PdfWriter.getInstance(document,
                new FileOutputStream("C:\\Users\\Jose Morente\\Documents\\top5-service.pdf"));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(500, 400);
            Graphics2D graphics2d = template.createGraphics(500, 400, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 500, 400);
            //CARGAR GRAFICA
            DefaultPieDataset dataSet = new DefaultPieDataset();
            ServiceAssignament asistente = tope;
            int quantity = 1;
            while(asistente != null) {
                System.out.println(asistente);
                if(quantity <= 5) {
                    if(asistente.getQuantity()!=0) {
                        dataSet.setValue(asistente.getService().getName(), asistente.getQuantity());
                        quantity++;
                    }
                }
                asistente = asistente.getNext();
            }
            JFreeChart chart = ChartFactory.createPieChart("TOP 5 SERVICES", dataSet, true, true, false);
            chart.draw(graphics2d, rectangle2d);
            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);
            
            //CARGAR DATOS TABLA
            Table table = new Table(5);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 255));
            table.setPadding(5);
            table.setSpacing(5);
            Cell cell = new Cell("SPARE PARTS TOP 5");
            cell.setHeader(true);
            cell.setColspan(5);
            table.addCell("ID");
            table.addCell("NAME");
            table.addCell("MARK");
            table.addCell("MODEL");
            table.addCell("QUANTITY");
            ServiceAssignament asistente2 = tope;
            int quantity2 = 1;
            while(asistente2 != null) {
                if(quantity2 <= 5) {
                    if(asistente2.getQuantity() != 0) {
                        table.addCell(String.valueOf(asistente2.getService().getId()));
                        table.addCell(String.valueOf(asistente2.getService().getName()));
                        table.addCell(String.valueOf(asistente2.getService().getMark()));
                        table.addCell(String.valueOf(asistente2.getService().getModel()));
                        table.addCell(String.valueOf(asistente2.getQuantity()));
                        quantity2++;
                    }
                }
                asistente2 = asistente2.getNext();
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
        try {
            File path = new File ("C:\\Users\\Jose Morente\\Documents\\top5-service.pdf");
            Desktop.getDesktop().open(path);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void getTopSpareParts() {
        //IMPRIMIR SERVICIOS SIN ORDENAR
        tope = null;
        SpareParts aux = null;
        aux = SparesPartsController.getInstance().first;
        
        while(aux != null) {
            System.out.println(aux.getName());
            WorkOrder work;
            work = WorkOrderController.getInstance().first;
            int quantity = 0;
            while(work != null) {
                //System.out.println(work);
                Stack aux2 =  work.getService().getSparePartList();
                Object[] pila = aux2.toArray();
                
                for (Object sp : pila) {
                    //System.out.println(sp);
                    SpareParts a = (SpareParts)sp;
                    System.out.println("COMPARAR ESTE VALOR " + a.getName() + " CON ESTE " +aux.getName());
                    if(aux.getName().equalsIgnoreCase(a.getName())) {
                        quantity++;
                    }
                }
                work = work.getNext();
            }
            System.out.println(quantity);
            addAssignmentSpareParts(new SparePartTopAssignament(aux, quantity));
            aux = aux.getNext();
        }
        
        //METODO DE ORDENAMIENTO DE BURBUJA
        SparePartTopAssignament aux1, aux2;
        aux2 = end;
        while (aux2 != null) {
            aux1 = aux2.getNext();
            while (aux1 != null) {
                if (aux1.getQuantity() > aux2.getQuantity()) {
                    SpareParts auxiliar = aux2.getService();
                    int auxiliar1 = aux2.getQuantity();
                    
                    aux2.setService(aux1.getService());
                    aux2.setQuantity(aux1.getQuantity());
                    
                    aux1.setService(auxiliar);
                    aux1.setQuantity(auxiliar1);
                }
                aux1 = aux1.getNext();
            }
            aux2 = aux2.getNext();
        }
        
        PdfWriter writer = null;
        Document document = new Document();
        try {
            writer = PdfWriter.getInstance(document,
                new FileOutputStream("C:\\Users\\Jose Morente\\Documents\\repuesto.pdf"));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(500, 400);
            Graphics2D graphics2d = template.createGraphics(500, 400, new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 500, 400);
            //CARGAR GRAFICA
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            SparePartTopAssignament asistente = end;
            int quantity = 1;
            while(asistente != null) {
                if(quantity <= 5) {
                    dataSet.setValue(asistente.getQuantity(), "Population", asistente.getService().getName());
                    quantity++;
                }
                asistente = asistente.getNext();
            }
            JFreeChart chart = ChartFactory.createBarChart(
                "TOP 5 SPARE PARTS", 
                "NAME", 
                "QUANTITY",
            dataSet, PlotOrientation.VERTICAL, false, true, false);
            chart.draw(graphics2d, rectangle2d);
            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);
            
            //CARGAR DATOS TABLA
            Table table = new Table(5);
            table.setBorderWidth(1);
            table.setBorderColor(new Color(0, 0, 255));
            table.setPadding(5);
            table.setSpacing(5);
            Cell cell = new Cell("TOP 5 SPARE PARTS");
            cell.setHeader(true);
            cell.setColspan(5);
            table.addCell("ID");
            table.addCell("NOMBRE");
            table.addCell("MARCA");
            table.addCell("MODELO");
            table.addCell("CANTIDAD");
            SparePartTopAssignament asistente2 = end;
            int quantity2 = 1;
            while(asistente2 != null) {
                if(quantity2 <= 5) {
                    table.addCell(String.valueOf(asistente2.getService().getId()));
                    table.addCell(String.valueOf(asistente2.getService().getName()));
                    table.addCell(String.valueOf(asistente2.getService().getMark()));
                    table.addCell(String.valueOf(asistente2.getService().getModel()));
                    table.addCell(String.valueOf(asistente2.getQuantity()));
                    quantity2++;
                }
                asistente2 = asistente2.getNext();
            }
                
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
        try {
            File path = new File ("C:\\Users\\Jose Morente\\Documents\\repuesto.pdf");
            Desktop.getDesktop().open(path);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        
        SparePartTopAssignament asistente = end;
        while(asistente != null) {
            System.out.println("CANTIDAD" + asistente.getQuantity() + "SERVICIO" + asistente.getService().getName());
            asistente = asistente.getNext();
        }
    }
        
    /**
     * ASIGNAR SERVICIO 
     */
    void addAssignmentService(ServiceAssignament temp){
        if(tope == null){
            tope = temp;
        }else{
            ServiceAssignament aux1, aux2;
            aux2 = tope;
            aux1 = tope.getNext();
            while(aux1!=null){
                aux1 = aux1.getNext();
                aux2 = aux2.getNext();
            }
            aux2.setNext(temp);
        }
    }
    
    /**
     * ASIGNAR SERVICIO 
     */
    void addAssignmentSpareParts(SparePartTopAssignament temp){
        if(end == null){
            end = temp;
        }else{
            SparePartTopAssignament aux1, aux2;
            aux2 = end;
            aux1 = end.getNext();
            while(aux1!=null){
                aux1 = aux1.getNext();
                aux2 = aux2.getNext();
            }
            aux2.setNext(temp);
        }
    }
    
}

class ServiceAssignament {
    private Service service;
    private int Quantity;
    private ServiceAssignament next;

    public ServiceAssignament(Service service, int Quantity) {
        this.service = service;
        this.Quantity = Quantity;
    }

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @return the next
     */
    public ServiceAssignament getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(ServiceAssignament next) {
        this.next = next;
    }
    
}

class SparePartTopAssignament {
    private SpareParts service;
    private int Quantity;
    private SparePartTopAssignament next;

    public SparePartTopAssignament(SpareParts service, int Quantity) {
        this.service = service;
        this.Quantity = Quantity;
    }

    /**
     * @return the service
     */
    public SpareParts getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(SpareParts service) {
        this.service = service;
    }

    /**
     * @return the Quantity
     */
    public int getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity the Quantity to set
     */
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * @return the next
     */
    public SparePartTopAssignament getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(SparePartTopAssignament next) {
        this.next = next;
    }
    
}