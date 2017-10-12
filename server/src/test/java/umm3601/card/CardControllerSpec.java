package umm3601.card;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.print.Doc;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the CardController.
 *
 * Created by mcphee on 22/2/17.
 */
public class CardControllerSpec
{
    private CardController cardController;

    @Before
    public void clearAndPopulateDB() throws IOException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> cardDocuments = db.getCollection("cards");
        cardDocuments.drop();
        List<Document> testCards = new ArrayList<>();
        testCards.add(Document.parse("{\n" +
            "                    word: \"Rugose\",\n" +
            "                    synonym: \"wrinkled\",\n" +
            "                    antonym: \"smooth\",\n" +
            "                    general_sense: \"pebbly or corrugated\",\n" +
            "                    example_usage: \"this frog is rugose\"\n" +
            "                }"));
        testCards.add(Document.parse("{\n" +
            "                    word: \"Blasphemous\",\n" +
            "                    synonym: \"Heresy\",\n" +
            "                    antonym: \"Holy\",\n" +
            "                    general_sense: \"that which is antithetical to an orthodoxy\",\n" +
            "                    example_usage: \"The earth moves around the sun was once considered such\"\n" +
            "                }"));
        cardDocuments.insertMany(testCards);

        // It might be important to construct this _after_ the DB is set up
        // in case there are bits in the constructor that care about the state
        // of the database.
        cardController = new CardController(db);
    }
    // http://stackoverflow.com/questions/34436952/jcard.containsKeyson-parse-equivalent-in-mongo-driver-3-x-for-java
    private BsonArray parseJsonArray(String json) {
        final CodecRegistry codecRegistry
            = CodecRegistries.fromProviders(Arrays.asList(
            new ValueCodecProvider(),
            new BsonValueCodecProvider(),
            new DocumentCodecProvider()));

        JsonReader reader = new JsonReader(json);
        BsonArrayCodec arrayReader = new BsonArrayCodec(codecRegistry);

        return arrayReader.decode(reader, DecoderContext.builder().build());
    }

    //Helper methods for testing
    private static String getWord(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("word")).getValue();
    }

    private static String getSynonym(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("synonym")).getValue();
    }

    private static String getAntonym(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("antonym")).getValue();
    }

    private static String getGeneral_sense(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("general_sense")).getValue();
    }

    private static String getExample_usage(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("example_usage")).getValue();
    }




    @Test
    public void getAllCards(){
        Map<String,String[]> emptyMap = new HashMap<>();
        String jsonResult = cardController.getCards(emptyMap);
        BsonArray cards = parseJsonArray(jsonResult);

        assertEquals("Should be 2 cards", 2, cards.size());
        List<String> words = cards
            .stream()
            .map(CardControllerSpec::getWord)
            .sorted()
            .collect(Collectors.toList());
        List<String> expectedWords = Arrays.asList("Blasphemous", "Rugose");
        assertEquals("Words should match", expectedWords, words);
    }

    @Test
    public void getCardInfo(){
        Map<String,String[]> emptyMap = new HashMap<>();
        String jsonResult = cardController.getCards(emptyMap);
        BsonArray cards = parseJsonArray(jsonResult);
        BsonValue tempcard =  cards.get(0);
        BsonDocument card = tempcard.asDocument();

        Assert.assertTrue(card.containsKey("word"));
        Assert.assertTrue(card.containsKey("synonym"));
        Assert.assertTrue(card.containsKey("antonym"));
        Assert.assertTrue(card.containsKey("general_sense"));
        Assert.assertTrue(card.containsKey("example_usage"));

        assertEquals("Should be Rugose", "Rugose", card.getString("word").getValue());
        assertEquals("Should be wrinkled", "wrinkled", card.getString("synonym").getValue());
        assertEquals("Should be smooth", "smooth", card.getString("antonym").getValue());
        assertEquals("Should be pebbly or corrugated", "pebbly or corrugated", card.getString("general_sense").getValue());
        assertEquals("Should be this frog is rugose", "this frog is rugose", card.getString("example_usage").getValue());
    }


    @Test
    public void addNewCard() {
        cardController.addNewCard("Colloquial", "conversational", "esoteric", "commonly understood lexicon", "He spoke with colloquial jargon.");

        Map<String, String[]> emptyMap = new HashMap<>();
        String jsonResult = cardController.getCards(emptyMap);
        BsonArray docs = parseJsonArray(jsonResult);

        assertEquals("There should now be 3 cards", 3, docs.size());
        List<String> words = docs
            .stream()
            .map(CardControllerSpec::getWord)
            .sorted()
            .collect(Collectors.toList());
        List<String> expectedWords = Arrays.asList("Blasphemous", "Colloquial", "Rugose");
        assertEquals("Words should match", expectedWords, words);
    }

    @Test
    public void checkNewCardAttributes() {
        cardController.addNewCard("Colloquial", "conversational", "esoteric", "commonly understood lexicon", "He spoke with colloquial jargon.");

        Map<String, String[]> emptyMap = new HashMap<>();
        String jsonResult = cardController.getCards(emptyMap);
        BsonArray docs = parseJsonArray(jsonResult);

        List<String> synonyms = docs
            .stream()
            .map(CardControllerSpec::getSynonym)
            .sorted()
            .collect(Collectors.toList());
        assertEquals("New synonym should be included among the cards",true,synonyms.contains("conversational"));
        List<String> antonyms = docs
            .stream()
            .map(CardControllerSpec::getAntonym)
            .sorted()
            .collect(Collectors.toList());
        assertEquals("New antonym should be included among the cards",true,antonyms.contains("esoteric"));
        List<String> general_senses = docs
            .stream()
            .map(CardControllerSpec::getGeneral_sense)
            .sorted()
            .collect(Collectors.toList());
        assertEquals("New general_sense should be included among the cards",true,general_senses.contains("commonly understood lexicon"));
        List<String> example_usages = docs
            .stream()
            .map(CardControllerSpec::getExample_usage)
            .sorted()
            .collect(Collectors.toList());
        assertEquals("New example_usage should be included among the cards",true,example_usages.contains("He spoke with colloquial jargon."));
    }
    //kept for reference in writing new tests.
//    @Test
//    public void getAllCards() {
//        Map<String, String[]> emptyMap = new HashMap<>();
//        String jsonResult = cardController.getCards(emptyMap);
//        BsonArray docs = parseJsonArray(jsonResult);
//
//        assertEquals("Should be 4 cards", 4, docs.size());
//        List<String> names = docs
//            .stream()
//            .map(CardControllerSpec::getName)
//            .sorted()
//            .collect(Collectors.toList());
//        List<String> expectedNames = Arrays.asList("Chris", "Jamie", "Pat", "Sam");
//        assertEquals("Names should match", expectedNames, names);
//    }
//
//    @Test
//    public void getCardsWhoAre37() {
//        Map<String, String[]> argMap = new HashMap<>();
//        argMap.put("age", new String[] { "37" });
//        String jsonResult = cardController.getCards(argMap);
//        BsonArray docs = parseJsonArray(jsonResult);
//
//        assertEquals("Should be 2 cards", 2, docs.size());
//        List<String> names = docs
//            .stream()
//            .map(CardControllerSpec::getName)
//            .sorted()
//            .collect(Collectors.toList());
//        List<String> expectedNames = Arrays.asList("Jamie", "Pat");
//        assertEquals("Names should match", expectedNames, names);
//    }
//
//    @Test
//    public void getSamById() {
//        String jsonResult = cardController.getCard(samsId.toHexString());
//        Document sam = Document.parse(jsonResult);
//        assertEquals("Name should match", "Sam", sam.get("name"));
//    }
}

//
//    @Test
//    public void getSamById() {
//        String jsonResult = cardController.getCard(samsId.toHexString());
//        Document sam = Document.parse(jsonResult);
//        assertEquals("Name should match", "Sam", sam.get("name"));
//    }
