# JUMG ('Jum-Gee')
### Java Utilities for Making Games
<hr>

### Contents
 1. What is JUMG?
 2. Who is 'BrennyTizer'?
 3. Why do I need JUMG?
 4. How to use JUMG
 5. Extra Notes
<hr>

### What is JUMG?
JUMG (pronounced 'Jum-Gee', acronymed 'Java Utilities for Making Games') is a library that contains multiple utilities to make Java Games the most efficient way. JUMG utilises the concepts of separating certain portions of code from the rest, to allow one main class to bring them altogether.

As JUMG is in development, it is necessary for a checklist to be made of what needs to be added, and what is added. This is here for simplicity's sake:
<br>**JUMG Development Checklist:**
 1. [x] Basic file reading system
 2. [x] Compression Algorithm
 3. [x] Frame and Screen implementation
 4. [ ] Graphical User Interface utilities *(will be continued as I go on...)*
 5. [x] Heads Up Display simplicity
 6. [x] Engine and cycle system
 7. [x] Sprite Manager
 8. [x] Level Manager
 9. [x] Better AI Management *(excluding the path webs)*
 10. [x] Sound Manager
 11. [x] Key Bindings
 12. [x] Entities
 13. [x] Inventory System
 13. [x] Save Game System
 14. [ ] Questing System
 15. [x] Event System
 16. [x] Collision detection
 17. [x] Networking
 18. [x] Version control and updating
 19. [x] Startup Arguments
 20. [ ] **Bulk Debugging Classes (currently working on...)**
 21. [ ] Full JavaDocs
 22. [ ] RELEASE!

### Who is BrennyTizer?
BrennyTizer is not a person, it is merely a blog/portfolio of one's work. That person who's work is being collected is Jarod Brennfleck. He is the guy who is constantly working hard to develop programs while achieving good grades at school. Currently a Year 11 student, Jarod has completed a few programs for some classes which he will be releasing soon online! For all the news and updates, check out his website at http://brennytizer.com.au/.

### Why do I need JUMG?
You don't * **NEED** * JUMG, it's simply a tool to speed up the process of creating games with Java. JUMG just simplifies the process by extinguishing the boring bits of code from your main class. Consider the following:

```java
// Without JUMG
public static void main(String[] args) {
  Dimension size = new Dimension(600, 500);
  JFrame jf = new JFrame("Title");
  jf.setPreferredSize(size);
  jf.setMinimumSize(size);
  jf.setMaximumSize(size);
  jf.setSize(size);
  jf.setLocationRelativeTo(null);
  jf.setFocusTraversalKeysEnabled(false);
  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  jf.setLayout(new BorderLayout());
  jf.add(new OverridenPanelWithAwesomeRender(), BorderLayout.CENTER);
  jf.pack();
  jf.setVisible(true);
}
```
This clump of code (plus extra, woo!) can be condensed down into just a few lines such as the following:
```java
// Using JUMG
public static void main(String[] args) {
  Dimension size = new Dimension(600, 500);
  Frame f = new Frame("Title", size);
  f.addDisplay(new Display(size, new MyRenderer(), null));
  f.show(true);
}
```
Now how much of a clamp-down is that?! JUMG just removes the boring code that we all dread to write repeatedly, and plonks it in nice little classes that allow you to have full control of. Yes, that's right. Nothing in JUMG has a private modifier - although it's bad practice - but as far as this library is concerned, there is no need to privatize anything. That frame that just got created in the JUMG example has a public JFrame in it's class. If you don't like it how you can't resize the frame, you can call the `JFrame.setMinimumSize(Dimension)` and `JFrame.setMaximumSize(Dimension)` by using `Frame.frame.set*Size`. Or you can set it to your own customized frame - provided it extends JFrame in some way!

###How to use JUMG
Due to JUMG's unique style of a multi-library mode, you can select which libraries you want to implement. This allows for a slim development of your game - if that's what you're after. There a not many necessities that come when it's time to implement, but one of these is the utils package is a must, no matter what package you're after. The utils package is an all round package which implements the most important tools into the library. The utils package contains methods such as logging, images, and certain units of measurement (which are annoying to implement with java - *cough* angles). But then again, that's what JUMG aims to do. Cut down your boring code so you can work faster.

So if the previous paragraph was a tl;dr for you, here are the simple steps:
#### If you only want some packages:
 1. Copy the `com.brennytizer.jumg.utils` package to your workspace.
 2. Copy your wanted packages across.
 3. The packages should be available for use!

#### If you want the full library (eclipse):
 PRE-REQ. Make sure the JUMG Library is a project in your workspace.
 1. Right Click your project > "Build Path" > "Configure Build Path..."
 2. "Projects" tab > "Add..."
 3. Tick the JUMG project you have made > OK > OK
 4. The packages should be available for use!

Note that these methods allow you to alter the packages, for your own personalization benefits, or to improve on JUMG. If you do make an improvement or addition that is not personalized, **push a request**!

### Extra Notes
JUMG is an open source tool that is freely available for modifications. Take it and throw it into eclipse, but remember that it would be nice that if you muddle around with it, that if you could throw a push request to the current JUMG build, here on GitHub.

JUMG uses the [Gnu GPL](http://www.gnu.org/licenses/gpl-3.0.txt) license to support modification of the works presented in this repository - however one should always remember to not redistribute the works under their name. I mean, come on man, that's not cool. Changes and additions to the works will be credited with a name, the work committed, and a slogan (provided that I approve of the slogan).

On another note about the license, if you need to hit the road but have already downloaded this library, and want to read the license, you can check the LICENSE.md file in the root of the library folder.

#### Disclamer
JUMG and BrennyPress are free softwares: you can
redistribute them and/or modify them under the terms of the
GNU General Public License as published by the Free Software
Foundation, either version 3 of the License any later
version.

JUMG and BrennyPress are distributed in the hope that they
will be useful, but WITHOUT ANY WARRANTY; without even the
implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for
more details.

You should have received a copy of the GNU General Public
License along with this software collection. If not, see
<http://www.gnu.org/licenses/>.
