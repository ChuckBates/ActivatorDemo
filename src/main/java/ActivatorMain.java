import Objects.ActivationRequest;
import Objects.ActivationResponse;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: cbates
 */
public class ActivatorMain extends JFrame {
    private static final String postUrl = "http://localhost:8080/ActivationService/Activate";

    public ActivatorMain() {
        initComponents();
    }

    public static void main(String[] args) {
        ActivatorMain activator = new ActivatorMain();
        activator.setTitle("Activation Service Demo");
        activator.setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(375, 200);
    }

    private void btnActivateActionPerformed() {
        if (isEmpty(txtEmail.getText()) || isEmpty(txtName.getText())) {
            JOptionPane.showMessageDialog(this, "Please enter required fields.", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ActivationRequest activationRequest = new ActivationRequest();
        activationRequest.setEmail(txtEmail.getText());
        activationRequest.setName(txtName.getText());

        URL url = null;
        Gson gson = new Gson();
        ActivationResponse activationResponse;
        OutputStream outputStream = null;
        InputStreamReader inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            url = new URL(postUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");

            outputStream = connection.getOutputStream();
            outputStream.write(gson.toJson(activationRequest).getBytes("UTF-8"));

            inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
            bufferedReader = new BufferedReader(inputStream);
            activationResponse = gson.fromJson(bufferedReader.readLine(), ActivationResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (activationResponse.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Activation was successful!!", "Activation Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Activation was NOT successful! ", "Activation Result", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        lblInstructions = new JLabel();
        lblEmail = new JLabel();
        txtEmail = new JTextField();
        lblName = new JLabel();
        txtName = new JTextField();
        btnActivate = new JButton();

        //======== this ========
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

        //---- lblInstructions ----
        lblInstructions.setText("Test The Activation Service");
        lblInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstructions.setName("lblInstructions");
        contentPane.add(lblInstructions, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblEmail ----
        lblEmail.setText("Email:");
        lblEmail.setName("lblEmail");
        contentPane.add(lblEmail, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- txtEmail ----
        txtEmail.setName("txtEmail");
        contentPane.add(txtEmail, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- lblName ----
        lblName.setText("Name:");
        lblName.setName("lblName");
        contentPane.add(lblName, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- txtName ----
        txtName.setName("txtName");
        contentPane.add(txtName, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));

        //---- btnActivate ----
        btnActivate.setText("Activate");
        btnActivate.setName("btnActivate");
        btnActivate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnActivateActionPerformed();
            }
        });
        contentPane.add(btnActivate, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel lblInstructions;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblName;
    private JTextField txtName;
    private JButton btnActivate;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
