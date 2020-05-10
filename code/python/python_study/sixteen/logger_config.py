import sys

from loguru import logger

logger.configure(
    handlers=[
        dict(
            sink=sys.stderr,
            backtrace=False,
            filter=lambda record: "default" in record["extra"],
        ),
        dict(
            sink="log/{time:YYYY-MM-DD}.log",
            filter=lambda record: "default" in record["extra"],
            backtrace=False,
            enqueue=True,
            level="INFO",
            rotation="00:00",
            retention="7 days",
        )
    ],
)

logger = logger.bind(default=True)
