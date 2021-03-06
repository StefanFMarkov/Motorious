package projectdefence.models.binding;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import projectdefence.validators.fieldMatch.FieldMatch;
import projectdefence.validators.password.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static projectdefence.messages.ExceptionMessages.*;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class EditProfileBindingModel {

    private String firstName;
    private String lastName;
    private String email;
    @ValidPassword
    private String password;
    private String confirmPassword;
    private MultipartFile image;

    public EditProfileBindingModel() {
    }


    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
    public String getFirstName() {
        return firstName;
    }

    public EditProfileBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }


    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 3, max = 50, message = LENGTH_BETWEEN_3_50_CHARACTERS)
    public String getLastName() {
        return lastName;
    }

    public EditProfileBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Email(message = INVALID_EMAIL)
    @Length(max = 150, message = INVALID_EMAIL)
    public String getEmail() {
        return email;
    }

    public EditProfileBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 6, max = 50, message = LENGTH_BETWEEN_6_50_CHARACTERS)
    public String getPassword() {
        return password;
    }

    public EditProfileBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotBlank(message = EMPTY_FIELD)
    @Length(min = 6, max = 50, message = LENGTH_BETWEEN_6_50_CHARACTERS)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public EditProfileBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public EditProfileBindingModel setImage(MultipartFile image) {
        this.image = image;
        return this;
    }
}
