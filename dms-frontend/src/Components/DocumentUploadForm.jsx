import React, { useState } from 'react';
import axios from 'axios';
import './DocumentUploadForm.css';

const DocumentUploadForm = () => {
  const [file, setFile] = useState(null);
  const [previewURL, setPreviewURL] = useState('');
  const [formData, setFormData] = useState({
    documentName: '',
    documentType: '',
    documentNumber: '',
    documentStatus: '',
    validFrom: '',
    validTo: '',
    subType: '',
    companyId: '',
    vendorId: '',
    employeeId: ''
  });

  const allowedTypes = ['application/pdf', 
                        'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];

    if (!allowedTypes.includes(selectedFile.type)) {
      alert("Only .pdf and .docx files are allowed.");
      return;
    }

    if (selectedFile.size > 4 * 1024 * 1024) {
      alert("File must be less than 4 MB.");
      return;
    }

    setFile(selectedFile);
    setPreviewURL(URL.createObjectURL(selectedFile));
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) return alert("Please select a valid file.");

    const data = new FormData();
    Object.entries(formData).forEach(([key, value]) => {
      data.append(key, value);
    });
    data.append('file', file);

    try {
      const res = await axios.post('/api/documents/upload', data, {
        headers: {
          'Authorization': 'Bearer sampletoken123', // Replace with actual bearer token logic
          'Content-Type': 'multipart/form-data',
        }
      });
      alert('Upload successful!');
    } catch (err) {
      console.log("ERR:",err);
      alert('Upload failed: ' + err.response.data);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="upload-form">
      <h2>Upload Document</h2>
      <input type="file" onChange={handleFileChange} />
      {previewURL && (
        <iframe title="file-preview" src={previewURL} width="100%" height="200px" />
      )}

      <input name="documentName" placeholder="Document Name" onChange={handleInputChange} required />
      <select name="documentType" onChange={handleInputChange} required>
        <option value="">Select Type</option>
        <option value="DL">DL</option>
        <option value="Passport">Passport</option>
        <option value="Work Authorization">Work Authorization</option>
        <option value="I-9">I-9</option>
        <option value="MSA">MSA</option>
        <option value="Purchase Order">Purchase Order</option>
        <option value="Background Check Report">Background Check Report</option>
        <option value="W-4">W-4</option>
      </select>

      {formData.documentType === 'Work Authorization' && (
        <select name="subType" onChange={handleInputChange}>
          <option value="">Select Sub-Type</option>
          <option value="H1-B">H1-B</option>
          <option value="H4 EAD">H4 EAD</option>
          <option value="L2 EAD">L2 EAD</option>
          <option value="GC">GC</option>
          <option value="OPT-EAD">OPT-EAD</option>
        </select>
      )}

      <input name="documentNumber" placeholder="Document Number" onChange={handleInputChange} required />
      <select name="documentStatus" onChange={handleInputChange} required>
        <option value="">Status</option>
        <option value="Active">Active</option>
        <option value="Expired">Expired</option>
      </select>

      <label>Valid From:</label>
      <input type="date" name="validFrom" onChange={handleInputChange} required />
      <label>Valid To:</label>
      <input type="date" name="validTo" onChange={handleInputChange} required />

      <input name="companyId" placeholder="Company ID" onChange={handleInputChange} required />
      <input name="vendorId" placeholder="Vendor ID" onChange={handleInputChange} required />
      <input name="employeeId" placeholder="Employee ID" onChange={handleInputChange} required />

      <button type="submit">Upload</button>
    </form>
  );
};

export default DocumentUploadForm;
