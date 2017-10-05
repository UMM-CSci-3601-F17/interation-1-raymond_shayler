package umm3601.card;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import spark.Request;
import spark.Response;

import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Controller that manages requests for info about cards.
 */
public class CardController {

    private final Gson gson;
    private MongoDatabase database;
    private final MongoCollection<Document> cardCollection;

    /**
     * Construct a controller for cards.
     *
     * @param database the database containing card data
     */
    public CardController(MongoDatabase database) {
        gson = new Gson();
        this.database = database;
        cardCollection = database.getCollection("cards");
    }


     /**
     * Get a JSON response with a list of all the cards in the database.
     *
     * @param req the HTTP request
     * @param res the HTTP response
     * @return one card in JSON formatted string and if it fails it will return text with a different HTTP status code
     */
    public String getCard(Request req, Response res){
        res.type("application/json");
        String id = req.params("id");
        String card;
        try {
            card = getCard(id);
        } catch (IllegalArgumentException e) {
            // This is thrown if the ID doesn't have the appropriate
            // form for a Mongo Object ID.
            // https://docs.mongodb.com/manual/reference/method/ObjectId/
            res.status(400);
            res.body("The requested card id " + id + " wasn't a legal Mongo Object ID.\n" +
                "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
            return "";
        }https://github.com/Clojure-Intro-Course/data-processing-and-tools.git
        if (card != null) {
            return card;
        } else {
            res.status(404);
            res.body("The requested card with id " + id + " was not found");
            return "";
        }
    }


    /**
     * Get the single card specified by the `id` parameter in the request.
     *
     * @param id the Mongo ID of the desired card
     * @return the desired card as a JSON object if the card with that ID is found,
     * and `null` if no card with that ID is found
     */
    public String getCard(String id) {
        FindIterable<Document> jsonCards
            = cardCollection
            .find(eq("_id", new ObjectId(id)));

        Iterator<Document> iterator = jsonCards.iterator();
        if (iterator.hasNext()) {
            Document card = iterator.next();
            return card.toJson();
        } else {
            // We didn't find the desired card
            return null;
        }
    }


    /**
     * @param req
     * @param res
     * @return an array of cards in JSON formatted String
     */
    public String getCards(Request req, Response res)
    {
        res.type("application/json");
        return getCards(req.queryMap().toMap());
    }

    /**
     * @param queryParams
     * @return an array of Cards in a JSON formatted string
     */
    public String getCards(Map<String, String[]> queryParams) {

        Document filterDoc = new Document();

        if (queryParams.containsKey("age")) {
            int targetAge = Integer.parseInt(queryParams.get("age")[0]);
            filterDoc = filterDoc.append("age", targetAge);
        }

        //FindIterable comes from mongo, Document comes from Gson
        FindIterable<Document> matchingCards = cardCollection.find(filterDoc);

        return JSON.serialize(matchingCards);
    }

    /**
     *
     * @param req
     * @param res
     * @return
     */
    public boolean addNewCard(Request req, Response res)
    {

        res.type("application/json");
        Object o = JSON.parse(req.body());
        try {
            if(o.getClass().equals(BasicDBObject.class))
            {
                try {
                    BasicDBObject dbO = (BasicDBObject) o;

                    String word = dbO.getString("word");
                    String synonym = dbO.getString("synonym");
                    String antonym = dbO.getString("antonym");
                    String general_sense = dbO.getString("general_sense");
                    String example_usage = dbO.getString("example_usage");

                    System.err.println("Adding new card [word=" + word + ", synonym=" + synonym + " antonym=" + antonym + " general_sense=" + general_sense + " example_usage=" + example_usage +']');
                    return addNewCard(word, synonym, antonym, general_sense, example_usage);
                }
                catch(NullPointerException e)
                {
                    System.err.println("A value was malformed or omitted, new card request failed.");
                    return false;
                }

            }
            else
            {
                System.err.println("Expected BasicDBObject, received " + o.getClass());
                return false;
            }
        }
        catch(RuntimeException ree)
        {
            ree.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param word
     * @param synonym
     * @param antonym
     * @param general_sense
     * @param example_usage
     * @return boolean true if successful
     */
    public boolean addNewCard(String word, String synonym, String antonym, String general_sense, String example_usage ) {

        Document newCard = new Document();
        newCard.append("word", word);
        newCard.append("synonym", synonym);
        newCard.append("antonym", antonym);
        newCard.append("general_sense", general_sense);
        newCard.append("example_usage", example_usage);

        try {
            cardCollection.insertOne(newCard);
        }
        catch(MongoException me)
        {
            me.printStackTrace();
            return false;
        }

        return true;
    }




}
