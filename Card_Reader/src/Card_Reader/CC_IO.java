/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Card_Reader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.security.cert.CertificateException;
import org.apache.commons.io.FilenameUtils;
import pteidlib.PTEID_ADDR;
import pteidlib.PTEID_ID;
import pteidlib.pteid;

/**
 *
 * @author Ricardo
 */
public class CC_IO {

    static {
        try {

            if (System.getProperty("os.name").split(" ")[0].equals("Windows")) {
                System.loadLibrary("pteidlibj");
            } else if (System.getProperty("os.name").split(" ")[0].equals("Linux")) {
                System.load("/usr/lib/pteid_jni/libpteidlibj.so.1.60.0");
            }

            //System.load("/home/rofler/libpteidlibj.so");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Não foi possivel carregar a biblioteca.\n" + e);
            System.exit(1);
        }
    }

    static int debug_morada = 0;

    public void PrintIDData(PTEID_ID idData) {
        StringBuilder sb = new StringBuilder();

        sb.append("\nNome : " + idData.firstname);
        sb.append("\nApelido: " + idData.name);

        sb.append("\n\nData de Nascimento: " + idData.birthDate);
        sb.append("\nAltura: " + idData.height);
        sb.append("\nSexo: " + idData.sex);

        sb.append("\n\nNome do pai: " + idData.firstnameFather);
        sb.append("\nApelido do pai: " + idData.nameFather);
        sb.append("\nNome da mãe: " + idData.firstnameMother);
        sb.append("\nApelido da mãe: " + idData.nameMother);

        sb.append("\n\nNúmero de Id. Civil: " + idData.numBI);
        sb.append("\nNúmero de Id. Fiscal: " + idData.numNIF);
        sb.append("\nNúmero do Serviço Nacional de Saúde: " + idData.numSNS);
        sb.append("\nNúmero da Segurança Social: " + idData.numSS);

        sb.append("\n\nNúmero do cartão: " + idData.cardNumber);
        sb.append("\nPAN (primary account number) do Cartão: " + idData.cardNumberPAN);
        sb.append("\nVersão do Cartão: " + idData.cardVersion);

        sb.append("\nNacionalidade: " + idData.nationality);
        sb.append("\nPaís: " + idData.country);

        sb.append("\nTipo de documento: " + idData.documentType);
        sb.append("\nData de entrega: " + idData.deliveryDate);
        sb.append("\nEntidade da Entrega: " + idData.deliveryEntity);
        sb.append("\nLocal: " + idData.locale);
        sb.append("\nMRZ 1: " + idData.mrz1);
        sb.append("\nMRZ 2: " + idData.mrz2);
        sb.append("\nMRZ 3: " + idData.mrz3);
        sb.append("\nNotas: " + idData.notes);

        System.out.println("Dados do cartão:\n" + sb.toString());
    }

    public CardData GetIDData(PTEID_ID idData) {
        //StringBuilder sb = new StringBuilder();

        CardData card = new CardData(idData.firstname, idData.name, idData.birthDate, idData.height,
                idData.sex, idData.firstnameFather, idData.nameFather, idData.firstnameMother, idData.nameMother,
                idData.numBI, idData.numNIF, idData.numSNS, idData.numSS, idData.cardNumber, idData.cardNumberPAN, idData.cardVersion,
                idData.nationality, idData.country, idData.documentType, idData.deliveryDate, idData.deliveryEntity, idData.locale,
                idData.mrz1, idData.mrz2, idData.mrz3, idData.notes);

        return card;

    }

    public void PrintAddressData(PTEID_ADDR adData) {
        if (adData.addrType == "N") {
            System.out.println("Tipo de morada : National");
            System.out.println("Rua : " + adData.street);
            System.out.println("Concelho : " + adData.municipality);
            System.out.println("...");
        } else {
            System.out.println("Tipo de morada : International");
            System.out.println("Endereço : " + adData.addressF);
            System.out.println("Cidade : " + adData.cityF);
            System.out.println("Rua : " + adData.street);
            System.out.println("Numero : " + adData.numMor);
            System.out.println("...");
        }
    }

    public CardData RunAnalisys(String photoPath) {

        CardData xpto = null;

        try {
            pteid.Init("");

            if (pteid.IsActivated() != 0) {
                System.err.println("cartao esta activado");
            } else {
                System.err.println("cartao NAO esta activado");
            }

            pteidlib.pteid.SetSODChecking(false);
            // Don't check the integrity of the ID, address and photo (!)
            //pteid.SetSODChecking(true);

            // Leitura de propriedades do "token" - Numero de serie e identificador do cartão - ECF#1
            pteidlib.PTEID_TokenInfo token = pteidlib.pteid.GetTokenInfo();
            System.out.println("Token: " + token.label + "\nN. Serie: " + token.serial);

            // Leitura dos certificados digitais do cartao - ECF#3
            pteidlib.PTEID_Certif[] certs = pteidlib.pteid.GetCertificates();
            System.out.println("Encontrados " + certs.length + " certificados");
            for (int i = 0; i < certs.length; i++) {
                try {
                    javax.security.cert.X509Certificate x509 = javax.security.cert.X509Certificate.getInstance(certs[i].certif);
                    //System.out.println("\nCertificado " + i + ":" + "\nDN do Certificado: " + x509.getSubjectDN() + "\nDN do Emissor" + x509.getIssuerDN() + "\nValido até: " + x509.getNotAfter());
                } catch (CertificateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Leitura de dados de identificacao do cidadao - ECF#5
            pteidlib.PTEID_ID idData = pteidlib.pteid.GetID();

            if (null != idData) {
                //System.out.println("Dados de identificação:");
                //PrintIDData(idData);
                xpto = GetIDData(idData);
            }
            /*
            // Leitura de dados da zona privada do cartao - ECF#7 
            byte[] filein = {0x3F, 0x00, 0x5F, 0x00, (byte) 0xEF, 0x07};
            byte[] data2;

            data2 = pteidlib.pteid.ReadFile(filein, (byte) 0x81);
            System.out.println("Dados da zona privada: " + new String(data2));

            // Escrita de dados na zona privada do cartao - ECF#9
            String data = "Teste de escrita na zona privada do cartão de cidadão";
            pteidlib.pteid.WriteFile(filein, data.getBytes(), (byte) 0x81);
            System.out.println("Dados escritos no cartão!");
             */
            // Leitura da morada do cidadao - ECF#11
            if (debug_morada == 1) {
                pteidlib.PTEID_ADDR adData = pteidlib.pteid.GetAddr();
                if (null != adData) {
                    System.out.println("Morada:");
                    PrintAddressData(adData);
                }

            }

            // Leitura da fotografia do cidadao - ECF#13
            pteidlib.PTEID_PIC picData = pteidlib.pteid.GetPic();
            if (null != picData) {
                try {
                    String photo = photoPath;
                    FileOutputStream oFile = new FileOutputStream(photo);
                    oFile.write(picData.picture);
                    oFile.close();
                    System.out.println("Created " + photo);
                } catch (FileNotFoundException excep) {
                    System.out.println(excep.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Leitura do SOD do cartao - ECF#15
            //System.out.println("SOD: " + new String(pteidlib.pteid.ReadSOD()));
            pteidlib.pteid.Exit(pteidlib.pteid.PTEID_EXIT_LEAVE_CARD);

        } catch (pteidlib.PteidException ex) {
            System.out.println(ex.getMessage());
            int errorNumber = Integer.parseInt(ex.getMessage().split("Error code : -")[1]);
            switch (errorNumber) {
                case 1101:
                    System.out.println("\nErro desconhecido - ??? Problemas com o serviço de leitor de cartões ???\nMessage: ");
                    break;
                case 1104:
                    System.out.println("\n\nNão foi possível aceder ao cartão.\nVerifique se está correctamente inserido no leitor");
                    break;
                case 1109:
                    System.out.println("\n\nAcção cancelada pelo utilizador.\n[Cancel na assinatura]");
                    break;
                case 1210:
                    System.out.println("\n\nO cartão inserido não corresponde a um cartão de cidadão válido.");
                    break;
                default:
                    System.out.println(ex.getMessage());
                    break;
            }
            ex.printStackTrace();
        }
        return xpto;
    }

}
