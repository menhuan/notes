import asyncio
from email import header
from pyppeteer import launch
async def main():
   browser = await launch(headless = False)
   page = await browser.newPage()
   await page.evaluateOnNewDocument('Object.defineProperty(navigator, "webdriver", {get: () => undefined})')
   await page.goto('https://www.nowcoder.com/discuss/experience?tagId=639#/')
   await asyncio.sleep(100)
asyncio.get_event_loop().run_until_complete(main())
    