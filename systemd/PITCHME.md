# systemd

---

## Units

0. Services
0. TODO

---

## Managing units

- `sudo systemctl start docker.service`
- `sudo systemctl stop docker.service`
- `sudo systemctl restart docker.service`
- `sudo systemctl reload docker.service`

---

## Units locations

- `/etc/systemd/system`
- `/run/systemd/system`
- `/lib/systemd/system`

Disable by creating empty file or linked to `/dev/null`

---

## Unit file

```
[Unit]
Description=Apache Kafka - broker
Documentation=http://docs.confluent.io/
Wants=zookeeper.target
After=network.target zookeeper.target

[Service]
Type=simple
User=kafka
Group=kafka
Environment=JMX_PORT=9581
EnvironmentFile=-/etc/default/kafka
ExecStart=/usr/bin/kafka-server-start /etc/kafka/server.properties
TimeoutStopSec=180
Restart=always
RestartSec=3
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

+++

### `[Unit]`

```
Description=Apache Kafka - broker
Documentation=http://docs.confluent.io/
Wants=zookeeper.target
After=network.target zookeeper.target
```

- `Requires` - starts all required units, fails otherwise
- `Wants` - starts all required units, does not fail on fail :)
- `Conflicts` - stops other services

- `Before`, `After` - defines order
