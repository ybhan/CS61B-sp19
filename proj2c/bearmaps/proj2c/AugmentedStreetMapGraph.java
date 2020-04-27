package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.lab9.TrieSet61B;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Yuanbo Han
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private Map<Point, Node> pointToNode;
    private PointSet kdTree;
    private Map<String, Set<String>> cleanedToFulls;  // cleaned name to Set of full names
    private Map<String, Set<Node>> nameToNodes;  // full name to Set of Nodes
    private TrieSet61B trie;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        pointToNode = new HashMap<>();
        cleanedToFulls = new HashMap<>();
        nameToNodes = new HashMap<>();
        trie = new MyTrieSet();

        for (Node node : this.getNodes()) {
            if (!this.neighbors(node.id()).isEmpty()) {
                pointToNode.put(new Point(node.lon(), node.lat()), node);
            }

            if (node.name() != null) {
                String cleanedName = cleanString(node.name());

                if (!cleanedToFulls.containsKey(cleanedName)) {
                    cleanedToFulls.put(cleanedName, new HashSet<>());
                }
                cleanedToFulls.get(cleanedName).add(node.name());

                if (!nameToNodes.containsKey(node.name())) {
                    nameToNodes.put(node.name(), new HashSet<>());
                }
                nameToNodes.get(node.name()).add(node);

                trie.add(cleanedName);
            }
        }

        kdTree = new KDTree(pointToNode.keySet());
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pointToNode.get(kdTree.nearest(lon, lat)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> fullNames = new ArrayList<>();
        for (String cleanedName : trie.keysWithPrefix(cleanString(prefix))) {
            fullNames.addAll(cleanedToFulls.get(cleanedName));
        }
        return fullNames;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedLocationName = cleanString(locationName);

        if (!cleanedToFulls.containsKey(cleanedLocationName)) {
            return null;
        }

        List<Map<String, Object>> locations = new ArrayList<>();
        for (String fullName : cleanedToFulls.get(cleanedLocationName)) {
            for (Node node : nameToNodes.get(fullName)) {
                Map<String, Object> loc = new HashMap<>();
                loc.put("lat", node.lat());
                loc.put("lon", node.lon());
                loc.put("name", node.name());
                loc.put("id", node.id());

                locations.add(loc);
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
