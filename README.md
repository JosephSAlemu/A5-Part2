![SE333_CI](https://github.com/JosephSAlemu/A5-Part2/actions/workflows/SE333_CI.yml/badge.svg)


## Manual UI Testing
I found that the manual UI Testing was confusing at first. I didn’t understand how Playwright recorded or how I would assert. After multiple failed attempts, I figured out how to assert for specific elements on the page. Once I had the entire script, I pasted it into one test method. Then, I had to manually add pauses to ensure that my test would fully pass. Once it did, I moved onto the CI github workflow file. I used the template provided from the PDF and pushed my files to github. However, github workflow failed because Playwright wasn’t being run headless. I made the modifications to my test file to make it headless and it passed the CI tests easily.


## AI-Assisted UI Testing
The ease of writing tests isn’t the same as the manual UI testing. Maybe it’s due to my lack of knowledge, but setting it up the mcp server was a bit difficult for me. Once I got the claude mcp server for Playwright up and running, I decided to use the LLM to write the test.
It’s first run through, it failed on test 2. I decided to manually modify the test based off of my test. Then It failed on another portion of test 2. This went back and forth for test 2-6 until I decided to go back to the LLM and ask it to run the files iteratively and make changes based off the test failures.
It worked for a tiny bit. I had to steer it and give it the failed output multiple times before it made a change that worked. I couldn’t get the LLM to get it to properly read the prices for some odd reason. I was going to paste my code, but I felt like it wouldn’t be a genuine approach from the LLM. I would say the accuracy of these tests is relatively low. I would give them around a 50% at best. The maintenance effort needed for these tests might be low because the LLM already structures the tests really well into separate files.
