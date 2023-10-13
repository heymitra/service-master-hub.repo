package repository.dto;

import entity.enumeration.ExpertStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
        return "id = " + id +
                "\nname = " + name +
                "\nsurname = " + surname +
                "\nemail = " + email +
//                "\npersonalPhoto = " + Arrays.toString(personalPhoto) +
                "\nscore = " + score +
                "\nexpertStatus = " + expertStatus + "\n-------------";
    }
}
