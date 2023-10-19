package repository.dto;

import entity.enumeration.ExpertStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class ExpertDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private byte[] personalPhoto;
    private int score;
    private ExpertStatusEnum expertStatus;

    @Override
    public String toString() {
        displayImage(personalPhoto);
        return "id = " + id +
                "\nname = " + name +
                "\nsurname = " + surname +
                "\nemail = " + email +
//                "\npersonalPhoto = " + Arrays.toString(personalPhoto) +
                "\nscore = " + score +
                "\nexpertStatus = " + expertStatus + "\n-------------";
    }

    private void displayImage(byte[] personalPhoto) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(personalPhoto);
            BufferedImage image = ImageIO.read(inputStream);

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Photo Viewer");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new JLabel(new ImageIcon(image)));
                frame.pack();
                frame.setVisible(true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
