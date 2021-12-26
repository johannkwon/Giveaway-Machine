import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reader {

    /**
     * TODO: comments must contain 3 unique instagram usernames
     * TODO: replies must contain 3 unique instagram usernames
     */

    /*
     * Examples of Graph API curl commands:
     * 
     * Format: curl -i -X GET \ "https://graph.facebook.com/v12.0/POST_ID?fields=comments&access_token=TOKEN"
     * 
     * curl -i -X GET \
     * "https://graph.facebook.com/v12.0/11111111111111?fields=replies&access_token=EAABZBFfRSxRsBAAHjXXLKPZCZA766orAzJvkjI3lmw6GL3KME34v9txjxYiNdcJIdOZBFPFmLgmQEN78ZBbGCuY3Un8ZBMbI0AXknZBXXMXxTiL5fpnsW2MIWI2CxQ6wWlrmKNJN5G6Fdv7zcrQSNLCSW6nCByuiHHfQ7aYaiiOvGguKZCpxPs0n7sVyLlGZBqfwF4iGAaaKZCwAZDZD"
     *
     * curl -i -X GET \
     * "https://graph.facebook.com/v12.0/22222222222222?fields=comments%7Breplies%7D&access_token=EAABZBFfRSxRsBAPMW95kp1biS7dkjZB6jbnXnawDZCVaznX6DxADc0yRFmZBXWGRQvyxOZChaIcJaOC8bGcVc33q10WbZA4ZC4o5DDYaIFcYPAPCvbjHDjxsDcg95gqFI3ylm608Rh0Gb0PH5rknonWnltTLdhIqCZBc8DfD0z51mMpUtGBsBCW3ZAFHkUcOqMPs8YSym9Futva8e8RECdlAI"
     */

    private String curlStart;
    private String atoken;
    private String postID;
    private Set<String> ids;

    public Reader() {
        curlStart = "curl -X GET \\ https://graph.facebook.com/v12.0/";
        atoken = "";
        postID = "";
        ids = new HashSet<String>();
    }

    /**
     * Creates comments.txt, ids.txt, and replies.txt
     * First deletes existing files then creates new ones
     */
    public void createResults() {
        try {
            File f = new File("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\comments.txt");
            if (f.delete()) {
                System.out.println("comments.txt was deleted");
            } else {
                System.out.println("comments.txt did not exist");
            }
            if (f.createNewFile()) {
                System.out.println("comments.txt was created!");
            } else {
                System.out.println("comments.txt is already there!");
            }
        } catch (IOException e) {
            System.out.println("Failed to create comments.txt");
        }

        try {
            File f = new File("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\ids.txt");
            if (f.delete()) {
                System.out.println("ids.txt was deleted");
            } else {
                System.out.println("ids.txt did not exist");
            }
            if (f.createNewFile()) {
                System.out.println("ids.txt was created!");
            } else {
                System.out.println("ids.txt is already there!");
            }
        } catch (IOException e) {
            System.out.println("Failed to create ids.txt");
        }

        try {
            File f = new File("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\replies.txt");
            if (f.delete()) {
                System.out.println("replies.txt was deleted");
            } else {
                System.out.println("replies.txt did not exist");
            }
            if (f.createNewFile()) {
                System.out.println("replies.txt was created!\n");
            } else {
                System.out.println("replies.txt is already there!\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to create replies.txt");
        }
    }

    /**
     * Gets all comments from post and stores them in comments.txt
     * 
     * @result all comments as a String
     */
    public String getComments() {
        String results = "";
        String comments = curlStart + postID + "?fields=comments" + "&access_token=" + atoken;
        ProcessBuilder commentsPB = new ProcessBuilder(comments.split(" "));
        commentsPB.directory(new File("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results"));

        try {
            Process commentsP = commentsPB.start();
            InputStream inputStream = commentsP.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            results = result;
            System.out.println(result);
            commentsP.destroy();
            try {
                FileWriter f = new FileWriter("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\comments.txt");
                f.write(result);
                f.close();
                System.out.println("Wrote to comments.txt\n");
            } catch (IOException e) {
                System.out.println("Failed to write to comments.txt\n");
            }
            s.close();

        } catch (IOException e) {
            System.out.println("Failed to create/start process");
        }

        return results;
    }

    /**
     * Gets all IDs from a String of comments (or replies) and stores them in
     * ids.txt
     * 
     * @param string of comments
     * @return set of ids as strings
     */
    public Set<String> getIDs(String comments) {
        try {
            FileWriter f = new FileWriter("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\ids.txt", true);
            BufferedWriter bf = new BufferedWriter(f);

            int idStart = 0;
            while (idStart < comments.length()) {
                idStart = comments.indexOf("\"id\":\"", idStart);
                int idEnd = comments.indexOf("\"}", idStart);

                if (idStart == -1 || idEnd == -1) {
                    break;
                }

                String nextID = comments.substring(idStart + 6, idEnd);

                if (!nextID.equals(postID)) { // add the id only if the id is not equal to the post's id (the post id
                                              // will be the last id in the comments string)
                    if (ids.add(nextID)) { // if adding to the set is possible, then write it to ids.txt
                        try {
                            bf.write(nextID);
                            bf.newLine();
                            System.out.println(nextID);
                        } catch (IOException e) {
                            System.out.println("Failed to write " + nextID + " to ids.txt");
                        }
                    }
                }
                idStart += 6;
            }
            bf.close();
            System.out.println("Wrote to ids.txt\n");
        } catch (IOException e) {
            System.out.println("Failed to create BufferedWriter\n");
        }
        return ids;
    }

    /**
     * Gets replies from a Set of IDs and returns them as a String of replies in
     * replies.txt
     * 
     * @param set of ids as strings
     * @return all replies as a String
     */
    /*
     * Gets replies from a Set of IDs and returns them as a String of replies in
     * replies.txt
     */
    public String getReplies(Set<String> ids) {
        String results = "";

        for (String commentID : ids) {
            String replies = curlStart + commentID + "?fields=replies" + "&access_token=" + atoken; // comments%7Breplies%7D
            ProcessBuilder repliesPB = new ProcessBuilder(replies.split(" "));
            repliesPB.directory(new File("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results"));
            try {
                Process repliesP = repliesPB.start();
                InputStream inputStream = repliesP.getInputStream();
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                System.out.println(commentID + " : " + result);
                if (result.indexOf("replies") != -1) { // only adds results with the keyword "replies" in them. results
                                                       // without the keyword arent replies; they don't exist
                    // System.out.println(result);
                    results += result;
                }
                repliesP.destroy();
                s.close();
            } catch (IOException e) {
                System.out.println("Failed to create/start process");
            }
        }

        try {
            FileWriter f = new FileWriter("D:\\VSCODE WORKSPACES\\Giveaway Machine\\results\\replies.txt");
            f.write(results);
            f.close();
            System.out.println("Wrote to replies.txt\n");
        } catch (IOException e) {
            System.out.println("Failed to write to replies.txt\n");
        }

        return results;
    }

    public static void main(String[] args) {
        Reader r = new Reader();
        r.createResults();
        r.getIDs(r.getReplies(r.getIDs(r.getComments())));
        for (String s : r.ids) {
            System.out.println(s);
        }
        System.exit(0);
    }

}