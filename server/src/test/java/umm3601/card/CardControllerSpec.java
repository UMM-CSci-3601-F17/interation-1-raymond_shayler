package umm3601.card;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
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
    // http://stackoverflow.com/questions/34436952/json-parse-equivalent-in-mongo-driver-3-x-for-java
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

    private static String getWord(BsonValue val) {
        BsonDocument doc = val.asDocument();
        return ((BsonString) doc.get("word")).getValue();
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
