(ns omnichromacy.core)

(defrecord ImageCube [specmat wavelengths units cols rows])
(defrecord Spectrum [specvec wavelengths units]) 

(defn spectrum
  [] nil)
(defn image-cube
  [] nil)
