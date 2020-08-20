import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './filiere.reducer';
import { IFiliere } from 'app/shared/model/filiere.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFiliereUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FiliereUpdate = (props: IFiliereUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { filiereEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/filiere');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...filiereEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="intranetApp.filiere.home.createOrEditLabel">
            <Translate contentKey="intranetApp.filiere.home.createOrEditLabel">Create or edit a Filiere</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : filiereEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="filiere-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="filiere-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idFiliereLabel" for="filiere-idFiliere">
                  <Translate contentKey="intranetApp.filiere.idFiliere">Id Filiere</Translate>
                </Label>
                <AvField
                  id="filiere-idFiliere"
                  type="string"
                  className="form-control"
                  name="idFiliere"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nomFiliereLabel" for="filiere-nomFiliere">
                  <Translate contentKey="intranetApp.filiere.nomFiliere">Nom Filiere</Translate>
                </Label>
                <AvField
                  id="filiere-nomFiliere"
                  type="text"
                  name="nomFiliere"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="abreviationLabel" for="filiere-abreviation">
                  <Translate contentKey="intranetApp.filiere.abreviation">Abreviation</Translate>
                </Label>
                <AvField
                  id="filiere-abreviation"
                  type="text"
                  name="abreviation"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/filiere" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  filiereEntity: storeState.filiere.entity,
  loading: storeState.filiere.loading,
  updating: storeState.filiere.updating,
  updateSuccess: storeState.filiere.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FiliereUpdate);
