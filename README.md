## Giveaway-Machine

Hello! This is a giveaway randomizer I made for Thrift Studio Co.

It has multiple randomizer functions:
- A normal randomizer that picks a winner(s)
- A tiered randomizer that allows you to set up different tiers of prizes and choose a winner(s) for each one

You can either use a preexisting text file that has names (one name per line) or manually input names with any amount of repeats.

Tiered randomization will choose from the entire pool, and chooses the results either top down (Tier 1 -> Tier 2 -> Tier 3) or bottom up (Tier 3 -> Tier 2 -> Tier 1).

## Reader.java
Reader.java gets the comments and replies from an Instagram Post using cURL functions and Facebook Graph API.

It stores comments and replies in comments.txt and replies.txt, respectfully. The IDs of each comment and reply are stored in ids.txt.


## To Do:
- Add 3 unique usernames as a constraint for comments and replies
- Get the username of the commenter for each comment/reply and store them in a file
- Combine GiveawayMachine and Reader into one cohesive whole