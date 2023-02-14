db.createUser(
    {
        user: "mongoUser",
        pwd: "mongoUser123",
        roles: [
            {
                role: "readWrite",
                db: "clientdb"
            }
        ]
    }
);