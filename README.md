# LoginSystem

This is so that we have a login queue.


Queue 101
=========
Let's say the player cap is at 75 and there are 75 people on.  
Yes, I am aware the current cap is 69.
  
_Scenario_
----------
Player A attempts to log in. He is placed #1 in the queue.  
Player B attempts to log in. She is placed #2 in the queue.  
Someone leaves the server, so now there are 74/75 people on.

---

What if Player C attempts to join?
----------------------------------
They are placed at #3 in the queue.


What if Player A doesn't join in time?
--------------------------------------
You have ~30 seconds after a slot opens to join the server.  
If you fail to login within that timeframe, you lose your spot.


What happens after Player A joins the server?
---------------------------------------------
Player B is moved to #1 in the queue.

---

Other FAQ
=========

Why is there a queue?
---------------------
Unfortunately, the reality of Vanilla mechanics means that we have very few ways to lighten the load on the server. Too many people causes too many entities, etc etc...  
We have a queue in order to make logging in a fair experience, even if not the most enjoyable for a time.

How do I check my spot in the queue?
------------------------------------
You have to attempt a login. It will tell you your current place in the queue.  
If you don't want to spam logins, I recommend watching Dynmap for people leaving to help estimate your position.


Does it save my spot?
---------------------
Yes.


Why do Mods get priority access?
--------------------------------
Moderators need to be able to do their job at a moments notice.  
This will **not** be changed.


Why do Mods count towards the cap, then?
----------------------------------------
If they didn't, the cap would be moot.  
If we have a cap of 75, it's because that's the ideal amount of players without destroying TPS.  
If a Mod logs in and it goes up to 76, we still want it to be 75. So when someone logs out, we don't want to replace them if possible until we go below 75 again.


You should/The queue should/The plugin should/Why doesn't it/etc
----------------------------------------------------------------
Yes, there are a _million_ ways to improve on a simple queue. The reality is, we probably won't even need the queue after a few weeks.  
If there _is_ still a need, I will look into improvements.  
If you have a suggestion, please **don't** bring it to my attention unless it's truly revolutionary.  
Yes, I know it could ping Discord.  
Yes, I know it could have a web interface.  
Sure, it could update the MOTD.  
The list goes on forever...  
