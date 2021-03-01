package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftCertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FillService {

    private String[] userNames = {
            "Sheri Pleasants" +
            "Trinity Gargano",
            "Sherill Schreckengost",
            "Galen Mai",
            "Nestor Ware",
            "Danita Berning",
            "Salome Topete",
            "Anamaria Janicki",
            "Rocio Rupert",
            "Simone Hoff",
            "Angelica Curto",
            "Dwayne Woolford",
            "Myrtis Humphery",
            "Mauricio Mckillop",
            "Heather Campos",
            "Elmer Borrego",
            "Genny Montalvan",
            "Jazmin Derby",
            "Willie Sylvestre",
            "Jeanene Duca",
            "Yoshie Holdren",
            "Sharon Govea",
            "Stanley Gurney",
            "Isela Baty",
            "Derek Avant",
            "Lovella Shenk",
            "Barbra Showers",
            "Kristy Deems",
            "Elvera Dunaway",
            "Walker Lore",
            "Ofelia Ahumada",
            "Ora Moler",
            "Marita Sturges",
            "Tomika Morain",
            "Young Tavera",
            "Javier Screws",
            "Ligia Record",
            "Delma Mcnulty",
            "Kimber Eck",
            "Elaina Slick",
            "Erik Husain",
            "Cory Sebree",
            "Tasia Lowenthal",
            "Diann Ohagan",
            "Wilfredo Flinchum",
            "Heath Drexler",
            "Piedad Coletti",
            "Lin Luevano",
            "Jeannie Cressman",
            "Teodora Callejas",
            "Jerald Moss",
            "Leida Liu",
            "Louann Speier",
            "Vanesa Vescio",
            "Lorine Mayne",
            "Josue Arends",
            "Horace Kelliher",
            "Maisie Montandon",
            "Iola Fogleman",
            "Chrissy Flinchbaugh",
            "Gertrud Flemming",
            "Crissy Thurston",
            "Dewey Branum",
            "Kate Riera",
            "Britni Marmo",
            "Liane Yard",
            "Kali Eidem",
            "Burt Allbritton",
            "Latonya Huges",
            "Florene Even",
            "Douglas Dusek",
            "Shiloh Tapp",
            "Beverlee Bonnet",
            "Lorretta Bevington",
            "Tynisha Holdridge",
            "Bobbi Sever",
            "Josefa Hust",
            "Annabell Turcotte",
            "Holli Suggs",
            "Matt Riedl",
            "Jarvis Mccauley",
            "Lore Shivers",
            "Deneen Hempel",
            "Glennis Hover",
            "Isabella Patague",
            "Margery Jerabek",
            "Inger Ables",
            "Sheryll Surratt",
            "Nova Gangestad",
            "Amira Babich",
            "Lenny Sherwin",
            "Herbert Kerbs",
            "Brooks Angell",
            "Monet Sitz",
            "Ossie Carballo",
            "Hortensia Wittner",
            "Mei Stowers",
            "Margy Hannon",
            "Donette Haubrich",
            "Carman Slawson"};

    private String[] tagNames = {"Party", "Ski", "Education", "Mountain", "Literature", "Book", "Tickets",
            "Football", "Basketball", "Volleyball", "Hockey", "Training", "Gym", "Piano", "Guitar", "Songs"};

    private String[] certificateNames = {"Pilling", "Super", "Puper", "Voyage", "Egypt", "Flowers", "Berlin",
            "Restaurant", "Bar", "Minsk", "Russia", "USA", "Ireland", "China", "Canada"};

    private String[] descriptions = {"lalala", "tratata", "gogogo", "fefefe", "dododo", "mememe", "tututu",
            "sososo", "lowlowlow", "sesese", "gavgavgav", "seekseekseek", "doordoordoor"};

    private RoleRepository roleRepository;
    private TagRepository tagRepository;
    private OrderRepository orderRepository;
    private GiftCertificateRepository giftCertificateRepository;
    private UserRepository userRepository;

    @Autowired
    public FillService(RoleRepository roleRepository,
                       TagRepository tagRepository,
                       OrderRepository orderRepository,
                       GiftCertificateRepository giftCertificateRepository,
                       UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.tagRepository = tagRepository;
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
    }

    //@Transactional
    public void fill() {
        /*fillTags();
        fillCertificates();
        fillUsers();*/
        fillOrders();
    }

    private void fillTags() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            String name = tagNames[random.nextInt(tagNames.length)];
            name += random.nextInt(1000000);
            Tag newTag = new Tag();
            newTag.setName(name);
            tagRepository.save(newTag);
        }
    }

    private void fillCertificates() {
        Random random = new Random();
        List<Tag> tags = tagRepository.findAll();
        for (int i = 0; i < 10000; i++) {
            int tagsNumber = random.nextInt(5);
            GiftCertificate newCertificate = new GiftCertificate();
            List<Tag> newTags = new ArrayList<>();
            for (int j = 0; j <= tagsNumber; j++) {
                Tag tag = tags.get(random.nextInt(tags.size()));
                newTags.add(tag);
            }
            newCertificate.setName(certificateNames[random.nextInt(certificateNames.length)] + random.nextInt(1000));
            newCertificate.setDescription(descriptions[random.nextInt(descriptions.length)] + random.nextInt(1000));
            newCertificate.setPrice(BigDecimal.valueOf(random.nextInt(5000) + 25));
            newCertificate.setDuration(random.nextInt(300) + 30);
            String nowIso = DateTimeUtility.getCurrentDateIso();
            newCertificate.setCreateDate(nowIso);
            newCertificate.setLastUpdateDate(nowIso);
            newCertificate.setTags(newTags);
            giftCertificateRepository.save(newCertificate);
        }
    }

    private void fillUsers() {
        Random random = new Random();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        for (int i = 0; i < 1000; i++) {
            User newUser = new User();
            String firstName = userNames[random.nextInt(userNames.length)].split(" ")[0];
            String lastName = userNames[random.nextInt(userNames.length)].split(" ")[1];
            String username = firstName.toLowerCase() + "_" + lastName.toLowerCase();
            if (userRepository.existsByUsername(username)) {
                i--;
            } else {
                newUser.setUsername(username);
                newUser.setPassword(passwordEncoder.encode("password"));
                newUser.setRole(roleRepository.findUserRole());
                newUser.setName(firstName + " " + lastName);
                userRepository.save(newUser);
            }
        }
    }

    private void fillOrders() {
        Random random = new Random();
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 30000; i++) {
            Order newOrder = new Order();
            GiftCertificate giftCertificate = giftCertificates.get(random.nextInt(giftCertificates.size()));
            User user = users.get(random.nextInt(users.size()));
            newOrder.setUser(user);
            newOrder.setGiftCertificate(giftCertificate);
            newOrder.setCost(giftCertificate.getPrice());
            newOrder.setOrderDate(DateTimeUtility.getCurrentDateIso());
            orderRepository.save(newOrder);
        }
    }
}
