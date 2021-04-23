package com.wrvpark.apiserver.configuration;

import com.wrvpark.apiserver.ApiserverApplication;
import com.wrvpark.apiserver.domain.*;
import com.wrvpark.apiserver.repository.*;
import com.wrvpark.apiserver.util.ConstantUtil;
import com.wrvpark.apiserver.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 * @author Vahid Haghighat
 * @author Chen Zhao
 *
 * Description: Initialized the database with generic data.
 */
@Component
public class InitializeDb implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ApiserverApplication.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ParkDocumentRepository parkDocumentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Seeding Users
//        String[][] users = {
//                {"cb96ab25-98c3-4370-93ff-08bfde00392a", "Admin"},
//                {"fe1fad44-0a14-4022-a05f-ba3bf5b75ef9", "Board"},
//                {"47457386-5285-4987-9c29-b0b7b1e10103", "Management"},
//                {"15084895-991d-43cb-ada4-f80f7ed53cc0", "Owner"},
//                {"0984088e-bc2b-4fe5-889e-fa35fe7b5a68", "Renter"},
//                {"5464f5c1-c84a-416c-8e11-924696d3fd88", "Unapproved"},
//                {"ea22d5c7-cd17-4498-ae09-068486240293", "Visitor"}
//        };
//        Role[] roles = new Role[] { new Role(false,false, false, false,true, false, false),
//                                    new Role(false, false, true, false, false, false, false),
//                                    new Role(false, false, false, true, false, false, false),
//                                    new Role(true, false, false, false, false, false, false),
//                                    new Role(false, true, false, false, false, false, false),
//                                    new Role(false, false, false, false, false, false, true),
//                                    new Role(false, false, false, false, false, true, false)};
//
//        Notification[] notifications = new Notification[users.length];
//
//        for(int i = 0; i < users.length; i++) {
//            if (!userRepository.existsById(users[i][0])) {
//                User user = new User(users[i][0], users[i][1], users[i][1], users[i][1].toLowerCase() + "@wrvpark.com");
//                notifications[i] = new Notification();
//                notifications[i].setService(true);
//                notifications[i].setSaleRent(true);
//                notifications[i].setEvent(true);
//                notifications[i].setLostFound(true);
//                notifications[i].setParkDocument(true);
//                notifications[i] = notificationRepository.save(notifications[i]);
//                user.setNotification(notifications[i]);
//                user.setRoles(roles[i]);
//                userRepository.save(user);
//            }
//        }
//
//        // Seeding Category and Subcategories
//        // Parkdocument
//        Category parkDocument = categoryRepository.findByName("Park Document");
//        if (parkDocument == null) parkDocument = categoryRepository.save(new Category("Park Document"));
//
//        String[] documentSubcategories = {"Notice", "Newsletter", "Minutes"};
//        for(String subName : documentSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, parkDocument));
//        }
//
//        Category forum = categoryRepository.findByName("Forum");
//        if (forum == null) forum = categoryRepository.save(new Category("Forum"));
//
//        String[] forumSubcategories = {"Board Members", "Park Management", "Other Park Members"};
//        for(String subName : forumSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, forum));
//        }
//
//        // Event
//        Category event = categoryRepository.findByName("Event");
//        if (event == null) event = categoryRepository.save(new Category("Event"));
//
//        // Locations Subcategories
//        String[] eventLocationSubcategories = {"Swimming Pool", "Parking Lot", "North end of Park"};
//        for (String subName : eventLocationSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, event, "location"));
//        }
//
//        // Description Subcategories
//        String[] eventDescSubcategories = {"Board Meeting", "Card Game", "Line Dancing"};
//        for (String subName : eventDescSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, event, "description"));
//        }
//
//        // For Sale or Rent
//        Category saleOrRent = categoryRepository.findByName("For Sale or Rent");
//        if (saleOrRent == null) saleOrRent = categoryRepository.save(new Category("For Sale or Rent"));
//
//        String[] forSaleRentSubcategories = {"Sale", "Rent", "Household", "Other"};
//        for(String subName : forSaleRentSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, saleOrRent));
//        }
//
//        // Services
//        Category services = categoryRepository.findByName("Services");
//        if (services == null) services = categoryRepository.save(new Category("Services"));
//
//        String[] servicesSubcategories = {"Offered", "Needed"};
//        for(String subName : servicesSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, services));
//        }
//
//        // Lost & Found
//        Category lostFound = categoryRepository.findByName("Lost & Found");
//        if (lostFound == null) lostFound = categoryRepository.save(new Category("Lost & Found"));
//
//        String[] lostFoundSubcategories = {"Lost", "Found"};
//        for(String subName : lostFoundSubcategories) {
//            Subcategory sub = subCategoryRepository.findByName(subName);
//            if (sub == null) sub = subCategoryRepository.save(new Subcategory(subName, lostFound));
//        }
//
//        Subcategory locationSub = subCategoryRepository.findByName("North end of Park");
//        Subcategory descSub = subCategoryRepository.findByName("Line Dancing");
//        User admin = userRepository.findById(users[0][0]).get();
//        String[] events = {
//                "John's Birthday Party",
//                "John's Football Game",
//                "John's Swimming Race",
//                "John's Game Night",
//                "John's Dancing Party",
//                "John's RV Race",
//                "John's Dota Cup",
//                "John's Fireworks Ceremony",
//                "John's Turkey Race",
//                "John's Flower Show"
//        };
//
//        for (String seedEvent : events) {
//            Event eventData = eventRepository.findByTitle(seedEvent.substring(7));
//            if(eventData == null){
//                Date date = TimeUtil.generateRandomFutureDate();
//                eventRepository.save(new Event(seedEvent,
//                        admin,
//                        seedEvent.substring(7),
//                        seedEvent,
//                        seedEvent,
//                        date,
//                        date,
//                        locationSub,
//                        descSub,
//                        true));
//            }
//        }
//
//        // Adding post from diff roles
//        createPostForUsers();
//        // Adding Items
//        createItems();
//        //Adding park documents data
//        createParkDocuments();
//
//        addPictures();

        LOG.info("All Done! API Server is ready to be used!");
    }

    private void createPostForUsers() {
        if (forumRepository.findAll().size() == 0) {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                String type;
                if (user.getFirstName().equalsIgnoreCase("Board"))
                    type = "Board";
                else if (user.getFirstName().equalsIgnoreCase("Admin") ||
                    user.getFirstName().equalsIgnoreCase("Management"))
                    type = "Management";
                else
                    type = "Member";
                for (int j = 1; j < 16; j++) {
                    Date forumDate = TimeUtil.generateRandomPastDate();
                    long startDate = forumDate.getTime();
                    Forum forum = new Forum(user.getFirstName() + "'s Post " + j,
                            "Post " + j,
                            type,
                            user,
                            forumDate,
                            "",
                            false);
                    forum = forumRepository.save(forum);
                    for (int k = 1; k < 11; k++) {
                        int responder = (int) (Math.random() * (users.size()));
                        Date responseDate = TimeUtil.generateRandomPastDate(startDate);
                        Response response = responseRepository.save(new Response(users.get(responder),
                                                         responseDate,
                                                         forum.getDetails() + " Response From " + users.get(responder).getFirstName(),
                                                         "",
                                                         forum));
                    }
                }
            }
        }
    }

    private void createItems() {
        if (itemRepository.findAll().size() == 0) {
            Subcategory[] lostFound = new Subcategory[2];
            lostFound[0] = subCategoryRepository.findByName("Lost");
            lostFound[1]  = subCategoryRepository.findByName("Found");

            Subcategory[] saleRent = new Subcategory[2];
            saleRent[0] = subCategoryRepository.findByName("Sale");
            saleRent[1] = subCategoryRepository.findByName("Rent");

            Subcategory[] services = new Subcategory[2];
            services[0] = subCategoryRepository.findByName("Offered");
            services[1] = subCategoryRepository.findByName("Needed");

            User admin = userRepository.findByFirstName("Admin");
            for (int i = 0; i < 10; i++) {
                itemRepository.save(new Item(admin,
                        "Lost and Found Item #" + (i + 1) + " Title",
                        "Lost and Found Item #" + (i + 1) + " Description",
                        (int)(Math.random() * 300),
                        ConstantUtil.TYPE_LOST_FOUND,
                        lostFound[(int)(Math.random() * lostFound.length)],
                        TimeUtil.generateRandomPastDate()));
                itemRepository.save(new Item(admin,
                        "Sale and Rent Item #" + (i + 1) + " Title",
                        "Sale and Rent Item #" + (i + 1) + " Description",
                        (int)(Math.random() * 300),
                        ConstantUtil.TYPE_SALE_RENT,
                        saleRent[(int)(Math.random() * saleRent.length)],
                        TimeUtil.generateRandomPastDate()));
                itemRepository.save(new Item(admin,
                        "Services Item #" + (i + 1) + " Title",
                        "Services Item #" + (i + 1) + " Description",
                        (int)(Math.random() * 300),
                        ConstantUtil.TYPE_SERVICES,
                        services[(int)(Math.random() * services.length)],
                        TimeUtil.generateRandomPastDate()));
            }
        }
    }

    // initialize park document table
    protected void createParkDocuments() {
        if (parkDocumentRepository.findAll().size() == 0) {
            Category parkDocumentCategory = categoryRepository.findByName("Park Document");
            List<Subcategory> subcategories = parkDocumentCategory.getSubcategoryList();
            System.out.println(subcategories.size());
            User admin = userRepository.findByFirstName("Admin");
            for (int j = 0; j < 50; j++){
                for(int i = 0; i < subcategories.size(); i++)
                {
                    ParkDocument parkDocument=new ParkDocument("Park document title "+(j+1),
                            subcategories.get(i),
                            admin,
                            TimeUtil.generateRandomPastDate(),
                            "Description "+(j+1),
                            "",
                            ConstantUtil.uploadFolder(),
                            ConstantUtil.STATUS_CREATED
                    );
                    parkDocumentRepository.save(parkDocument);
                }
            }
        }
    }

    private void addPictures() {
        if (pictureRepository.findAll().size() == 0) {
            String[] pictures = new String[]
            {
                "https://i.ibb.co/zn2yS2P/homepage-picture-1.jpg",
                "https://i.ibb.co/KywGjLJ/Motorhome-Traveling-with-Dog-Small-Silky-Terrier-Dog-in-Camper-Van-Window-Dog-in-RV-Motorhome.jpg",
                "https://i.ibb.co/j4cgNVZ/Modern-Camper-Van-in-the-Summer-Sun-Camping-and-Travel-Theme.jpg",
                "https://i.ibb.co/80cBcFL/Camping-together-Happy-mature-couple-hugging-each-other-on-family-vacation-near-their-trailer-Panora.jpg",
                "https://i.ibb.co/JCYc16g/Family-vacation-travel-holiday-trip-in-motorhome-Caravan-car-Vacation-VR-Beautiful-Nature-Italy-natu.jpg",
                "https://i.ibb.co/FhfmNpF/homepage-picture-7.jpg",
                "https://i.ibb.co/QC8bpF6/Recreational-Vehicle-Camping-Vacation-in-a-Travel-Trailer-RV-Theme.jpg",
                "https://i.ibb.co/xYrbZ52/homepage-picture-10.jpg",
                "https://i.ibb.co/XSdzhkM/Modern-Motorhome-Interior-Elegant-Materials-and-Finishing-Motorcoaching-Theme.jpg",
                "https://i.ibb.co/mDV6yL6/Travel-Trailer-Caravaning-RV-Park-Camping-at-Night.jpg",
                "https://i.ibb.co/5LvsXCc/Woman-is-standing-with-a-mug-of-coffee-near-the-camper-Caravan-car-Vacation-Family-vacation-travel-h.jpg",
                "https://i.ibb.co/kgKX7hj/homepage-picture-14.jpg",
                "https://i.ibb.co/Gd4LCZ8/Camper-Van-Summer-Trip-Scenic-Norway-Landscape-and-the-Recreational-Vehicle.jpg"
            };

            for (String picture : pictures)
                pictureRepository.save(new Picture( picture));
        }
    }
}