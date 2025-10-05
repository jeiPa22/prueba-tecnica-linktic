-- datos semilla.
INSERT INTO
    products (
        id,
        name,
        price,
        currency,
        status,
        created_at,
        updated_at
    )
VALUES
    (
        gen_random_uuid (),
        'Teclado Mecánico Azul',
        199000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Teclado Mecánico Rojo',
        209000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Teclado Mecánico Low-Profile',
        259000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Mouse Inalámbrico Pro',
        129000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Mouse Gamer 8K DPI',
        189000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Mouse Ergo Silent',
        99000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Monitor 24" FHD 75Hz',
        599000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Monitor 27" QHD 165Hz',
        1299000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Monitor 34" Ultrawide',
        1899000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Audífonos ANC BT',
        349000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Audífonos In-Ear',
        149000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Audífonos Studio',
        599000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Micrófono USB Cardioide',
        299000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Micrófono XLR',
        449000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Mousepad XL',
        69000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Mousepad RGB',
        99000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Webcam 1080p',
        189000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Webcam 4K',
        499000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Silla Ergonómica Malla',
        899000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Silla Gamer',
        1199000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Soporte Monitor Gas',
        259000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Soporte Portátil',
        99000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Hub USB-C 6 en 1',
        149000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Hub USB-C 10 en 1',
        299000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Dock Thunderbolt 4',
        899000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'SSD NVMe 500GB',
        279000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'SSD NVMe 1TB',
        449000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'SSD SATA 1TB',
        329000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'HDD 2TB 7200rpm',
        299000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Router WiFi 6 AX3000',
        399000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Router WiFi 7',
        999000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Teclado 60% Wireless',
        219000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Teclado 75% Hot-Swap',
        359000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Switches Lineales (set 35)',
        89000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Switches Táctiles (set 35)',
        99000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Keycaps PBT ISO',
        159000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Keycaps PBT ANSI',
        159000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Bocinas 2.0 Compactas',
        129000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Bocinas 2.1 Subwoofer',
        299000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Barra de Sonido PC',
        249000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Cámara IP 2K',
        199000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Cámara IP PTZ',
        359000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Lámpara Escritorio LED',
        79000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Tira LED RGB 5m',
        99000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Supresor 8 tomas',
        79000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'UPS 1200VA',
        459000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Cargador USB-C 65W',
        149000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Cargador USB-C 100W',
        229000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Cable USB-C 2m 100W',
        59000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Adaptador HDMI 4K',
        79000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Adaptador DP 8K',
        129000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Funda Laptop 14"',
        69000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Funda Laptop 16"',
        89000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Base Refrigerante Laptop',
        129000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Tablet 10" 64GB',
        799000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Tablet 11" 128GB',
        999000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Lápiz Digital',
        249000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Smartwatch GPS',
        549000,
        'COP',
        'ACTIVE',
        now (),
        now ()
    ),
    (
        gen_random_uuid (),
        'Banda Deportiva',
        199000,
        'COP',
        'INACTIVE',
        now (),
        now ()
    );