import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;

public class DOM {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        try {
            Scanner sc = new Scanner(System.in);
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabrica.newDocumentBuilder();
            Document documento = constructor.parse(new File("C:\\Users\\WIN10\\Downloads\\sales.xml"));

            NodeList registrosVenta = documento.getElementsByTagName("registro_venta");
            System.out.print("Ingrese el nombre del departamento: ");
            String departamento = sc.nextLine().trim();

            System.out.print("Ingrese el porcentaje de incremento (5%-15%): ");
            double incremento = Double.parseDouble(sc.nextLine().trim());

            for (int i = 0; i < registrosVenta.getLength(); i++) {
                Element registroVenta = (Element) registrosVenta.item(i);
                String departamentoActual = registroVenta.getElementsByTagName("departamento").item(0).getTextContent();

                if (departamentoActual.equals(departamento)) {
                    double valorActual = Double.parseDouble(registroVenta.getElementsByTagName("ventas").item(0).getTextContent());
                    double nuevoValor = valorActual * (1 + incremento / 100);
                    if (!Double.isNaN(nuevoValor) && !Double.isInfinite(nuevoValor)) {
                        registroVenta.getElementsByTagName("ventas").item(0).setTextContent(String.format("%.2f", nuevoValor));
                    } else {
                        System.out.println("El nuevo valor no es válido.");
                    }
                }
            }

            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            Transformer transformador = fabricaTransformador.newTransformer();
            DOMSource fuente = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File("new_sales.xml"));
            transformador.transform(fuente, resultado);
            System.out.println("El documento XML ha sido modificado y se guardó en un nuevo documento XML con el nombre: ventas_modificadas.xml.");

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
