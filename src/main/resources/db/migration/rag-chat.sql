CREATE TABLE session (
    session_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(250) NOT NULL DEFAULT 'TEXT',
    user_id VARCHAR(50) NOT NULL,
    is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
    status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE INDEX idx_sessions_user_id ON session(user_id);
CREATE INDEX idx_sessions_session_id ON session(session_id);

CREATE TABLE message (
    message_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sender VARCHAR(10) NOT NULL,          -- USER or ASSISTANT
    content TEXT NOT NULL,                 -- Message text
    rag_context TEXT,                     -- Retrieved RAG documents (AI only)
    model_metadata TEXT,                  -- AI model metadata (AI only),
	session_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
	
    
    CONSTRAINT fk_session
        FOREIGN KEY (session_id)
        REFERENCES session(session_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_message_session_id ON message(session_id);